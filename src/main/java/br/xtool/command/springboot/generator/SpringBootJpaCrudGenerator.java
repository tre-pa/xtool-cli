package br.xtool.command.springboot.generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.FS;
import br.xtool.core.Names;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.impl.EJavaEntityImpl;
import br.xtool.core.representation.provider.EJavaEntityValueProvider;
import strman.Strman;

/**
 * Comando que gera um classe CRUD no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaCrudGenerator extends SpringBootCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "gen:crud", value = "Gera as classes de CRUD (Repository, Service e Rest) para entidade JPA", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJavaEntityValueProvider.class) EJavaEntityImpl entity) {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("Strman", Strman.class);
		vars.put("templatePath", "springboot/1.5.x/crud");
		vars.put("entity", entity);
		vars.put("groupId", this.getProject().getPom().getGroupId());
		vars.put("repositoryName", Names.asRepositoryClass(entity.getName()));
		vars.put("serviceName", Names.asServiceClass(entity.getName()));
		vars.put("restName", Names.asRestClass(entity.getName()));
		vars.put("restPath", Names.asRestPath(entity.getName()));
			
		
		this.fs.copy("${templatePath}/projection/projection.java.vm", "src/main/java/${groupId.dir}/repository/projection/${entity.name}Info.java", vars);
		this.fs.copy("${templatePath}/repository/repository.java.vm", "src/main/java/${groupId.dir}/repository/${repositoryName}.java", vars);
		this.fs.copy("${templatePath}/service/service.java.vm", "src/main/java/${groupId.dir}/service/${serviceName}.java", vars);
		this.fs.copy("${templatePath}/rest/rest.java.vm", "src/main/java/${groupId.dir}/rest/${restName}.java", vars);

	}

	
}
