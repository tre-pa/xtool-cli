package br.xtool.command.springboot.support.core;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject.Type;

public interface SpringBootSupport {

	public Type getApplyForType();

	public SupportType getType();

	public void apply(EBootProject project);

	public boolean hasSupport(EBootProject project);

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
