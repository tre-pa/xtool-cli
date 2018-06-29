package br.xtool.generator.springboot;

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
import br.xtool.core.NamePattern;
import br.xtool.core.command.SpringBootCommand;

/**
 * Comando que gera uma classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaEntityGenerator extends SpringBootCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "gen-springboot-jpa-entity", value = "Gera uma classe de entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da entidade JPA") String name) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("entityName", NamePattern.asEntityClass(name))
				.put("tableName", NamePattern.asDBTable(name))
				.put("seqName", NamePattern.asDBSequence(name))
				.build();
		// @formatter:on

		fs.copy("generators/springboot/entity/entity.java.vm", "src/main/java/${groupId.dir}/domain/${entityName}.java", vars);
	}

}
