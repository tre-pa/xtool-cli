package br.xtool.command.springboot.generator;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.FS;
import br.xtool.core.Names;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.provider.EEntityValueProvider;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaRepositoryGenerator extends SpringBootCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EEntityValueProvider.class) EEntity entity) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("repositoryName", Names.asRepositoryClass(entity.getName()))
				.put("entity", entity)
				.build();
		// @formatter:on

		this.fs.copy("springboot/repository/repository.java.vm", "src/main/java/${groupId.dir}/repository/${repositoryName}.java", vars);
	}
}
