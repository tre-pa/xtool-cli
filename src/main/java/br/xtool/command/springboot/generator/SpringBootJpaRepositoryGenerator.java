package br.xtool.command.springboot.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.impl.EJavaEntityImpl;
import br.xtool.core.representation.provider.EJavaEntityValueProvider;
import br.xtool.core.service.FileService;
import br.xtool.core.util.Names;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaRepositoryGenerator extends SpringBootCommand {

	@Autowired
	private FileService fs;

	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJavaEntityValueProvider.class) EJavaEntityImpl entity) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("groupId", this.getProject().getPom().getGroupId());
		vars.put("repositoryName", Names.asRepositoryClass(entity.getName()));
		vars.put("entity", entity);
		// @formatter:on

		this.fs.copy("springboot/1.5.x/repository/repository.java.vm", "src/main/java/${groupId.dir}/repository/${repositoryName}.java", vars);
	}
}
