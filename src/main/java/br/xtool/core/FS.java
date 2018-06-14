package br.xtool.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FS {

	@Autowired
	private VelocityEngine vEngine;

	/**
	 * Realiza uma cópia simples.
	 * 
	 * @param source
	 *            Caminho relativo do arquivo no diretório de templates.F
	 * @param destination
	 *            Caminho absoluto do arquivo final
	 * @throws IOException
	 */
	public void copy(String source, String destination) throws IOException {
		FileUtils.forceMkdirParent(new File(destination));
		FileUtils.copyInputStreamToFile(new ClassPathResource(String.format("templates/%s", source)).getInputStream(),
				new File(destination));
	}

	/**
	 * Realiza uma cópia com substituição de variáveis no template.
	 * 
	 * @param template
	 *            Caminho relativo do arquivo de template
	 * @param destination
	 *            Caminho absoluto do arquivo final
	 * @param vars
	 *            Mapa com variáveis para substituição no template
	 * @throws IOException
	 */
	public void copyTpl(String template, String destination, Map<String, Object> vars) throws IOException {
		Template t = vEngine.getTemplate(String.format("templates/%s", template));
		VelocityContext vContext = new VelocityContext(vars);
		FileUtils.forceMkdirParent(new File(destination));
		FileWriter writer = new FileWriter(destination);
		t.merge(vContext, writer);
		writer.flush();
		writer.close();
	}
}
