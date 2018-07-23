package br.xtool.core.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EJavaSourceFolder;
import lombok.SneakyThrows;

@Service
public class FileService {

	@Autowired
	private VelocityEngine vEngine;

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * Realiza uma cópia com substituição de variáveis no template.
	 * 
	 * @param templatePath
	 *            Caminho relativo do arquivo de template
	 * @param relativeDestination
	 *            Caminho relativo do arquivo final
	 * @param vars
	 *            Mapa com variáveis para substituição no template
	 * @throws IOException
	 */
	public void copy(String templatePath, String relativeDestination, Map<String, Object> vars) {
		this.copy(templatePath, relativeDestination, vars, false);
	}

	/**
	 * 
	 * @param templatePath
	 * @param relativeDestination
	 * @param vars
	 * @param binary
	 * @throws IOException
	 */
	@SneakyThrows
	public void copy(String templatePath, String relativeDestination, Map<String, Object> vars, boolean binary) {
		VelocityContext vContext = new VelocityContext(vars);
		templatePath = this.inlineTemplate(templatePath, vars);
		relativeDestination = this.inlineTemplate(relativeDestination, vars);
		//		String finalDestination = FilenameUtils.concat(this.workspaceService.getWorkingProject().getDirectory().getPath(), relativeDestination);
		String finalDestination = null;
		if (!Files.exists(Paths.get(finalDestination))) {
			FileUtils.forceMkdirParent(new File(finalDestination));
			if (binary) {
				FileUtils.copyInputStreamToFile(new ClassPathResource(String.format("templates/%s", templatePath)).getInputStream(), new File(finalDestination));
				ConsoleLog.print(ConsoleLog.bold(ConsoleLog.green("\t[+] ")), ConsoleLog.purple("File: "), ConsoleLog.white(relativeDestination));
				return;
			}
			Template t = this.vEngine.getTemplate(String.format("templates/%s", templatePath), "UTF-8");
			FileWriterWithEncoding writer = new FileWriterWithEncoding(finalDestination, "UTF-8");
			t.merge(vContext, writer);
			writer.flush();
			writer.close();
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.green("\t[+] ")), ConsoleLog.purple("File: "), ConsoleLog.white(relativeDestination));
			return;
		}
		ConsoleLog.print(ConsoleLog.bold(ConsoleLog.gray("\t[!] ")), ConsoleLog.purple("File: "), ConsoleLog.gray(relativeDestination), ConsoleLog.yellow(" -- Skip "));
	}

	/**
	 * Realiza substituição inline do template.
	 * 
	 * @param inlineTemplate
	 * @param vars
	 * @return
	 */
	public String inlineTemplate(String inlineTemplate, Map<String, Object> vars) {
		VelocityContext vContext = new VelocityContext(vars);
		StringWriter stringWriter = new StringWriter();
		this.vEngine.evaluate(vContext, stringWriter, new String(), inlineTemplate);
		return stringWriter.toString();
	}

	/**
	 * Cria um diretório vazio.
	 * 
	 * @param relativeHomeDestination
	 * @param vars
	 */
	public void createEmptyPath(String relativeHomeDestination, Map<String, Object> vars) {
		try {
			relativeHomeDestination = this.inlineTemplate(relativeHomeDestination, vars);
			//			String finalDestination = FilenameUtils.concat(this.workspaceService.getHome().getPath(), relativeHomeDestination);
			String finalDestination = null;
			if (!Files.exists(Paths.get(finalDestination))) {
				FileUtils.forceMkdir(new File(finalDestination));
				FileUtils.touch(new File(FilenameUtils.concat(finalDestination, ".gitkeep")));
				ConsoleLog.print(ConsoleLog.bold(ConsoleLog.green("\t[+] ")), ConsoleLog.purple("Path: "), ConsoleLog.white(relativeHomeDestination));
				return;
			}
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.gray("\t[!] ")), ConsoleLog.purple("Path: "), ConsoleLog.gray(relativeHomeDestination), ConsoleLog.yellow(" -- Skip "));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sourceFolder
	 * @param name
	 * @param vars
	 */
	public void createEmptyPath(EJavaSourceFolder sourceFolder, String name, Map<String, Object> vars) {
		//		this.createEmptyPath(FilenameUtils.concat(sourceFolder.getPath(), name), vars);
	}

	/**
	 * 
	 * @param sourceFolder
	 * @param name
	 */
	public void createEmptyPath(EJavaSourceFolder sourceFolder, String name) {
		//		this.createEmptyPath(sourceFolder.getPath(), new HashMap<>());
	}
}
