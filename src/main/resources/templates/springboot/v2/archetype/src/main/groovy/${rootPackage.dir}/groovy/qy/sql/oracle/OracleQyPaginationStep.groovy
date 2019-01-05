package br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.oracle

import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlPageableSelectStep
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlPaginationStep

class OracleQyPaginationStep implements QySqlPaginationStep {

	JdbcTemplate jdbcTemplate

	Map sqlFragments = [:]

	public OracleQyPaginationStep(Map sqlFragments, JdbcTemplate jdbcTemplate) {
		super()
		this.sqlFragments = sqlFragments
		this.jdbcTemplate = jdbcTemplate
	}

	@Override
	public List<Map<String, Object>> fetchMaps() {
		jdbcTemplate.queryForList(sqlFragments['sql'])
	}

	@Override
	public QySqlPageableSelectStep limit(Pageable pageable) {
		def sql = sqlFragments['whereSql'] ? sqlFragments['whereSql'] : sqlFragments['sourceSql']
		sqlFragments['countSql'] = "select count(0) from (${sql})  "
		if(pageable.pageNumber == 0) {
			sqlFragments['sql'] = "select * from ( ${sqlFragments['sql']} ) where rownum <= ${pageable.pageSize}"
			return new OracleQyPageableSelectStep(jdbcTemplate, sqlFragments, pageable);
		}
		sqlFragments['sql'] = " select * from (  select row_.*, rownum rownum_ from ( ${sqlFragments['sql']} ) row_ where rownum <=  ${(pageable.pageSize*pageable.pageNumber)+pageable.pageSize} ) where  rownum_ > ${pageable.pageSize*pageable.pageNumber}"
		return new OracleQyPageableSelectStep(jdbcTemplate, sqlFragments, pageable);
	}
}
