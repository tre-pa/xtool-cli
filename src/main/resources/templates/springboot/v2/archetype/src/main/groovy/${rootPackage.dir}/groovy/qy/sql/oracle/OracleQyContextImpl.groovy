package br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.oracle

import org.apache.commons.lang3.StringUtils
import org.springframework.jdbc.core.JdbcTemplate

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.QyAggregation
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.QyPayload
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.QyFilterable.EmptyQyFilterable
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlContext
import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlWhereStep

class OracleQyContextImpl implements QySqlContext {

	JdbcTemplate jdbcTemplate

	Map sqlFragments = [:]

	public OracleQyContextImpl(JdbcTemplate jdbcTemplate) {
		super()
		this.jdbcTemplate = jdbcTemplate
	}

	@Override
	public QySqlWhereStep selectFrom(String sql) {
		def selectSql = StringUtils.prependIfMissing(sql, "select * from (")
		selectSql = StringUtils.appendIfMissing(selectSql, " ) ")

		sqlFragments['sourceSql'] = sql
		sqlFragments['sql'] = selectSql

		return new OracleQyWhereStep(jdbcTemplate,sqlFragments)
	}

	@Override
	public List<QyAggregation> aggregation(String sql, QyPayload payload) {
		if(!payload?.aggregables) return null;
		List<QyAggregation> aggregrations = payload.aggregables.collect { 
			def whereSql = payload.filterable instanceof EmptyQyFilterable  ? "":  "where ${payload.filterable.toSql()}";
			def aggSql = "select ${it.operation.name()}(${it.dataField}) from ( ${sql} ) ${whereSql} "; 
			def result = jdbcTemplate.queryForObject(aggSql, Object.class);
			new QyAggregation(dataField: it.dataField, operation: it.operation, result: result);
		}
		return aggregrations
	}

	@Override
	public void clear() {
		sqlFragments.clear()
	}
}
