package br.xtool.core.representation.enums;

public enum ProjectType {
	// @formatter:off
	SPRINGBOOT1_PROJECT("Projeto Spring Boot v1.5.x"),
	SPRINGBOOT2_PROJECT("Projeto Spring Boot v2.x.x"),
	ANGULAR5_PROJECT("Projeto Angular v5"), 
	ANGULAR6_PROJECT("Projeto Angular v6"),
	NONE("");
	// @formatter:on

	private String detail;

	private ProjectType(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

}
