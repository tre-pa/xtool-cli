package br.xtool.core.support;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;

public class CacheSupport implements BootProjectSupport {

	@Override
	public void apply(EBootProject project) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void apply(EBootAppProperties appProperties) {
		// @formatter:off
		appProperties
			.set("spring.jpa.properties.hibernate.cache.use_second_level_cache", "true")
			.set("spring.jpa.properties.hibernate.cache.use_query_cache", "true")
			.set("spring.jpa.properties.hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory")
			.set("logging.level.net.sf.ehcache", "DEBUG")
		.save();
	// @formatter:on
	}

	@Override
	public void apply(EBootPom pom) {
		// @formatter:off
		pom
		  .addDependency("org.springframework.boot", "spring-boot-starter-data-jpa")
		  .addDependency("com.h2database", "h2")
		  .addDependency("org.hibernate", "hibernate-java8")
		  .addDependency("com.oracle", "ojdbc6", "11.2.0.3.0")
		.save();
		// @formatter:on

	}

	@Override
	public boolean has(EBootProject project) {
		return false;
	}

}
