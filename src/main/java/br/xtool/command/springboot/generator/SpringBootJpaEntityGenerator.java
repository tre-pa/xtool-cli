package br.xtool.command.springboot.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.diagram.ClassDiagramReader;
import br.xtool.core.service.FileService;
import br.xtool.core.util.Names;

/**
 * Comando que gera uma classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaEntityGenerator extends SpringBootCommand {

	@Autowired
	private FileService fs;

	@Autowired
	private ClassDiagramReader diagramReader;

	@ShellMethod(key = "gen:entity", value = "Gera uma classe de entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da entidade JPA", defaultValue = "") String name) throws IOException, JDOMException {
		this.diagramReader.parse(FileUtils.readFileToString(new File("/home/jcruz/git/sb1-service/docs/diagrams/class.md"), "UTF-8"));
		this.diagramReader.write(this.getProject());
		//generateFromTemplate(name);
	}

	private void generateFromTemplate(String name) {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("groupId", this.getProject().getPom().getGroupId());
		vars.put("entityName", Names.asEntityClass(name));
		vars.put("tableName", Names.asDBTable(name));
		vars.put("seqName", Names.asDBSequence(name));
		// @formatter:on

		this.fs.copy("springboot/1.5.x/entity/entity.java.vm", "src/main/java/${groupId.dir}/domain/${entityName}.java", vars);
	}

}
