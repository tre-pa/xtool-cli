package br.xtool.core.service.impl;

import static br.xtool.core.ConsoleLog.bold;
import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jboss.forge.roaster.Roaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.converter.EUmlClassConverter;
import br.xtool.core.representation.converter.EUmlFieldConverter;
import br.xtool.core.representation.converter.EUmlRelationshipConverter;
import br.xtool.core.visitor.Visitor;
import br.xtool.service.BootService;
import lombok.SneakyThrows;

@Service
public class BootServiceImpl implements BootService {

	@Autowired
	private ApplicationContext applicationContext;

	//	@Autowired
	//	private JavaxValidationVisitor javaxValidationVisitor;

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
		Properties prefs = new Properties();
		prefs.setProperty(JavaCore.COMPILER_SOURCE, CompilerOptions.VERSION_1_8);
		prefs.setProperty(JavaCore.COMPILER_COMPLIANCE, CompilerOptions.VERSION_1_8);
		prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, CompilerOptions.VERSION_1_8);
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "120");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD, "1");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_IMPORTS, "1");

		//		prefs.setProperty(DefaultCodeFormatterConstants., "TRUE");
		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
			String formatedJavaClassSource = Roaster.format(prefs, javaClass.getRoasterJavaClass().toUnformattedString());
			write.write(formatedJavaClassSource);
			write.flush();
			sourceFolder.getBootProject().refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#convertUmlClassDiagramToJavaClasses(br.xtool.core.representation.EBootProject)
	 */
	@Override
	public void convertUmlClassDiagramToJavaClasses(EBootProject bootProject, Set<Visitor> vistors) {
		// @formatter:off
		Collection<EJavaClass> javaClasses = bootProject.getDomainClassDiagram().getClasses().stream()
				.map(umlClass -> convertUmlClassToJavaClass(bootProject, umlClass, vistors))
				.collect(Collectors.toList());
		// @formatter:on
		javaClasses.stream().forEach(javaClass -> this.save(bootProject.getMainSourceFolder(), javaClass));
		print(bold(cyan(String.valueOf(javaClasses.size()))), " classes mapeadas.");
	}

	// Converte uma classe UML para um objeto EJavaClass.
	private EJavaClass convertUmlClassToJavaClass(EBootProject bootProject, EUmlClass umlClass, Set<? extends Visitor> vistors) {
		EJavaClass javaClass = new EUmlClassConverter(vistors).apply(bootProject, umlClass);
		umlClass.getFields().stream().forEach(umlField -> new EUmlFieldConverter(vistors).apply(javaClass, umlField));
		umlClass.getRelationships().stream().forEach(umlRelationship -> new EUmlRelationshipConverter(vistors).apply(javaClass, umlRelationship));
		return javaClass;
	}

}
