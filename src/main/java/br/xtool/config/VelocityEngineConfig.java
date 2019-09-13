package br.xtool.config;

import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VelocityEngineConfig {

	@Bean
	public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "class");
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		return ve;
	}
}
