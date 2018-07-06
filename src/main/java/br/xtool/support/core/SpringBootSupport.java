package br.xtool.support.core;

import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;

public interface SpringBootSupport {

	public ProjectType getApplyForType();

	public SupportType getType();

	public void apply(ESpringBootProject project);
	
	public boolean hasSupport(ESpringBootProject project);
	
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
