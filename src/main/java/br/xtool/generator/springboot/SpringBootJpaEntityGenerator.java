package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.NamePattern;
import br.xtool.core.annotation.Template;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;
import strman.Strman;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
@Template(path = "generators/springboot/entity")
public class SpringBootJpaEntityGenerator extends SpringBootCommand {

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

		this.copyTpl("entity.java.vm", "src/main/java/${groupId.dir}/domain/${entityName}.java", vars);
	}

}
