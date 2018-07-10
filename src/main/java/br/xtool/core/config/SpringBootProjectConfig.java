package br.xtool.core.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ESpringBootProject;

@Configuration
public class SpringBootProjectConfig {

	@Autowired
	private WorkContext workContext;

	@Bean
	public Optional<ESpringBootProject> springBootProject() {
		return ESpringBootProject.of("/home/jcruz/git/sb1-service");
	}
}
