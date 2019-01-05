package br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.oracle


import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlPageableSelectStep

class OracleQyPageableSelectStep implements QySqlPageableSelectStep {

	JdbcTemplate jdbcTemplate

	Map sqlFragments = [:]
	
	Pageable pageable;

	public OracleQyPageableSelectStep(JdbcTemplate jdbcTemplate, Map sqlFragments, Pageable pageable) {
		super()
		this.jdbcTemplate = jdbcTemplate
		this.sqlFragments = sqlFragments
		this.pageable = pageable;
	}

	@Override
	public Page<List<Map<String, Object>>> fetchMaps() {
		def count = jdbcTemplate.queryForObject(sqlFragments['countSql'], Long.class);
		def result = jdbcTemplate.queryForList(sqlFragments['sql']);
		return new PageImpl(result, pageable, count);
	}
}
