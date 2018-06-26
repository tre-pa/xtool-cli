package br.xtool.core;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
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
	 * @param templatePath
	 *            Caminho relativo do arquivo no diretório de templates.F
	 * @param relativeDestination
	 *            Caminho absoluto do arquivo final
	 * @throws IOException
	 */
	public void copy(String templatePath, String relativeDestination) throws IOException {
		FileUtils.forceMkdirParent(new File(relativeDestination));
		FileUtils.copyInputStreamToFile(new ClassPathResource(String.format("templates/%s", templatePath)).getInputStream(), new File(relativeDestination));
	}

	/**
	 * Realiza uma cópia com substituição de variáveis no template.
	 * 
	 * @param templatePath
	 *            Caminho relativo do arquivo de template
	 * @param relativeDestination
	 *            Caminho absoluto do arquivo final
	 * @param vars
	 *            Mapa com variáveis para substituição no template
	 * @throws IOException
	 */
	public void copy(String templatePath, String relativeDestination, Map<String, Object> vars) throws IOException {
		VelocityContext vContext = new VelocityContext(vars);

		StringWriter stringWriter = new StringWriter();
		vEngine.evaluate(vContext, stringWriter, new String(), relativeDestination);
		relativeDestination = stringWriter.toString();

		Template t = vEngine.getTemplate(String.format("templates/%s", templatePath), "UTF-8");
		FileUtils.forceMkdirParent(new File(relativeDestination));
		FileWriterWithEncoding writer = new FileWriterWithEncoding(relativeDestination, "UTF-8");
		t.merge(vContext, writer);
		writer.flush();
		writer.close();
	}
}
