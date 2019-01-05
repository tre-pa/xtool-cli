package br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.oracle

import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Sort
import org.springframework.jdbc.core.JdbcTemplate

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlOrderByStep
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlPaginationStep

class OracleQyOrderByStep implements QySqlOrderByStep {

	JdbcTemplate jdbcTemplate

	Map sqlFragments = [:]

	public OracleQyOrderByStep(Map sqlFragments, JdbcTemplate jdbcTemplate) {
		super()
		this.sqlFragments = sqlFragments
		this.jdbcTemplate = jdbcTemplate
	}

	@Override
	public List<Map<String, Object>> fetchMaps() {
		jdbcTemplate.queryForList(sqlFragments)
	}

	@Override
	public QySqlPaginationStep orderBy(String sortSql) {
		if(StringUtils.isNotBlank(sortSql)) {
			sqlFragments['sql'] = "${sqlFragments['sql']} order by ${sortSql} "
		}
		new OracleQyPaginationStep(sqlFragments, jdbcTemplate)
	}

	@Override
	public QySqlPaginationStep orderBy(Sort sort) {
		String orders = sort?.orders.collect { "${it.property} ${it.direction} " }.join(",")
		orderBy(orders);
	}
}
