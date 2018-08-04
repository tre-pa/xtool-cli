package br.xtool.core.service.impl;

import static br.xtool.core.ConsoleLog.bold;
import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.service.BootService;
import br.xtool.core.util.Inflector;
import br.xtool.core.util.RoasterUtil;
import lombok.SneakyThrows;

@Service
public class BootServiceImpl implements BootService {

	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#addSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootSupport> void addSupport(EBootProject bootProject, Class<T> supportClass) {
		this.applicationContext.getBean(supportClass).apply(bootProject);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#hasSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass) {
		return this.applicationContext.getBean(supportClass).has(bootProject);
	}

	@Override
	@SneakyThrows
	public void save(EJavaSourceFolder sourceFolder, EJavaClass javaClass) {
		Path javaPath = sourceFolder.getPath().resolve(javaClass.getPackage().getDir()).resolve(String.format("%s.java", javaClass.getName()));
		if (Files.notExists(javaPath.getParent())) Files.createDirectories(javaPath);
		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
			write.write(javaClass.getRoasterJavaClass().toString());
			write.flush();
			sourceFolder.getBootProject().refresh();
		}
	}

	@Override
	public void convertUmlClassDiagramToJavaClasses(EBootProject bootProject) {
		// @formatter:off
		Collection<EJavaClass> javaClasses = bootProject.getDomainClassDiagram().getClasses().stream()
				.map(umlClass -> convertUmlClassToJavaClass(bootProject, umlClass))
				.collect(Collectors.toList());
		// @formatter:on
		javaClasses.stream().forEach(javaClass -> this.save(bootProject.getMainSourceFolder(), javaClass));
		print(bold(cyan(String.valueOf(javaClasses.size()))), " classes mapeadas.");
	}

	private EJavaClass convertUmlClassToJavaClass(EBootProject bootProject, EUmlClass umlClass) {
		// @formatter:off
		EJavaClass javaClass = bootProject.getRoasterJavaUnits().stream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(umlClass.getName()))
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.map(javaClassSource -> new EJavaClassImpl(bootProject, javaClassSource))
			.findFirst()
			.orElseGet(() -> new EJavaClassImpl(bootProject,RoasterUtil.createJavaClassSource(umlClass.getPackage().getName(),umlClass.getName())));
		// @formatter:on
		umlClass.getFields().stream().forEach(umlField -> convertUmlFieldToJavaField(javaClass, umlField));
		umlClass.getRelationships().stream().forEach(umlRelationship -> convertUmlRelationshipToJavaField(javaClass, umlRelationship));
		return javaClass;
	}

	// Converte um atributo da classe UML para um objeto EJavaField.
	private void convertUmlFieldToJavaField(EJavaClass javaClass, EUmlField umlField) {
		EJavaField javaField = javaClass.addField(umlField.getName());
		RoasterUtil.addImport(javaField.getRoasterField().getOrigin(), umlField.getType().getClassName());
		// @formatter:off
		javaField.getRoasterField()
			.setName(umlField.getName())
			.setPrivate()
			.setType(umlField.getType().getJavaName());
		// @formatter:on
	}

	// Converte um relacionamento UML para um objeto EJavaField.
	private void convertUmlRelationshipToJavaField(EJavaClass javaClass, EUmlRelationship umlRelationship) {
		String fieldName = Inflector.getInstance().pluralize(StringUtils.uncapitalize(umlRelationship.getTargetClass().getName()));
		EJavaField javaField = javaClass.addField(fieldName);
		if (umlRelationship.getSourceMultiplicity().isToMany()) {
			// @formatter:off
			javaField.getRoasterField().getOrigin().addImport(List.class);
			javaField.getRoasterField().getOrigin().addImport(ArrayList.class);
			javaField.getRoasterField()
					.setPrivate()
					.setName(fieldName)
					.setType(String.format("List<%s>", umlRelationship.getTargetClass().getName()))
					.setLiteralInitializer("new ArrayList<>()");
			// @formatter:on
			return;
		}
		javaField.getRoasterField().getOrigin().addImport(umlRelationship.getTargetClass().getQualifiedName());
		// @formatter:off
		javaField.getRoasterField()
				.setPrivate()
				.setName(StringUtils.uncapitalize(umlRelationship.getTargetClass().getName()))
				.setType(umlRelationship.getTargetClass().getName());
		// @formatter:on
	}
}
