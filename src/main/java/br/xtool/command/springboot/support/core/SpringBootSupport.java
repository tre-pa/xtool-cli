package br.xtool.command.springboot.support.core;

import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.EProject.ProjectType;

public interface SpringBootSupport {

	public ProjectType getApplyForType();

	public SupportType getType();

	public void apply(ESBootProject project);

	public boolean hasSupport(ESBootProject project);

	// @formatter:off
	public enum SupportType {
		JPA,
		JPA_AUDIT,
		FLYWAY,
		EHCACHE,
		REPORT,
		HIBERNATE_SEARCH,
		WEB,
		SWAGGER,
		ASYNC,
		SCHEDULE,
		EMAIL,
		WEBSOCKET,
		KEYCLOAK,
		LOMBOK
	}
	// @formatter:on

}
