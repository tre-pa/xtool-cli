package br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.oracle

import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Sort
import org.springframework.jdbc.core.JdbcTemplate

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.QyFilterable
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.QyFilterable.EmptyQyFilterable
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlOrderByStep
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlPaginationStep
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlWhereStep

class OracleQyWhereStep implements QySqlWhereStep {

	JdbcTemplate jdbcTemplate

	Map sqlFragments = [:]

	public OracleQyWhereStep(JdbcTemplate jdbcTemplate, Map sqlFragments) {
		super()
		this.jdbcTemplate = jdbcTemplate
		this.sqlFragments = sqlFragments
	}

	@Override
	public List<Map<String, Object>> fetchMaps() {
		jdbcTemplate.queryForList(sqlFragments['sql']);
	}


	@Override
	public QySqlOrderByStep where(QyFilterable filter) {
		if(filter instanceof EmptyQyFilterable == false) {
			sqlFragments['whereSql'] = sqlFragments['sql'] = "${sqlFragments['sql']} where ${filter.toSql()} "
		}
		new OracleQyOrderByStep(sqlFragments, jdbcTemplate)
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
