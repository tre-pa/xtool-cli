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

	@ShellMethod(key = "gen:entity", value = "Gera uma classe de entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da entidade JPA") String name) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("entityName", Names.asEntityClass(name))
				.put("tableName", Names.asDBTable(name))
				.put("seqName", Names.asDBSequence(name))
				.build();
		// @formatter:on

		this.fs.copy("springboot/1.5.x/entity/entity.java.vm", "src/main/java/${groupId.dir}/domain/${entityName}.java", vars);
	}

}
