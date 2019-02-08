package br.xtool.core.representation.impl;

import static br.xtool.core.ConsoleLog.bold;
import static br.xtool.core.ConsoleLog.gray;
import static br.xtool.core.ConsoleLog.print;
import static br.xtool.core.ConsoleLog.purple;
import static br.xtool.core.ConsoleLog.white;
import static br.xtool.core.ConsoleLog.yellow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import br.xtool.core.representation.springboot.ApplicationPropertiesRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.SneakyThrows;
import strman.Strman;

public class ApplicationPropertiesRepresentationImpl implements ApplicationPropertiesRepresentation {

	private Path path;

	private PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();

	private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

	private FileBasedConfiguration configuration;

	private SpringBootProjectRepresentation bootProject;

	private ApplicationPropertiesRepresentationImpl(SpringBootProjectRepresentation bootProject, Path path) throws ConfigurationException {
		super();
		this.path = path;
		this.bootProject = bootProject;
		this.layout.setGlobalSeparator("=");
		Parameters params = new Parameters();
		this.builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class);
		// @formatter:off
		this.builder.configure(params.properties()
				.setLayout(this.layout)
				.setFileName(path.toString()));
		// @formatter:on
		this.configuration = this.builder.getConfiguration();
		//		configuration.

	}

	@Override
	public SpringBootProjectRepresentation getProject() {
		return this.bootProject;
	}

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(this.configuration.getString(key));
	}

	@Override
	public ApplicationPropertiesRepresentation set(String key, String value) {
		if (!this.configuration.containsKey(key)) {
			this.configuration.setProperty(key, value);
			print(bold(yellow("\t[~] ")), purple("Item: "), white("application.properties"), gray(" -- "), gray(Strman.surround(key, "Key [", "]")));
		}
		return this;
	}

	@Override
	public ApplicationPropertiesRepresentation set(String key, String value, Object... params) {
		return this.set(key, String.format(value, params));
	}

	@Override
	public ApplicationPropertiesRepresentation comment(String key, String value) {
		this.layout.setComment(key, value);
		return this;
	}

	@Override
	public boolean hasProperty(String key) {
		return this.configuration.containsKey(key) && this.get(key).isPresent();
	}

	@Override
	@SneakyThrows
	public void save() {
		this.builder.save();
	}

	public static ApplicationPropertiesRepresentation of(SpringBootProjectRepresentation bootProject, Path path) {
		if (Files.exists(path)) {
			try {
				ApplicationPropertiesRepresentationImpl representation = new ApplicationPropertiesRepresentationImpl(bootProject, path);
				return representation;
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Não foi possível localizar ou ler o arquivo src/main/resources/application.properties. Verifique se o mesmo existe o contém erros.");

	}
}
