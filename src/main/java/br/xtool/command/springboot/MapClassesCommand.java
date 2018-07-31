package br.xtool.command.springboot;

import static br.xtool.core.ConsoleLog.bold;
import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.service.BootService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.visitor.impl.JpaVisitor;

@ShellComponent
public class MapClassesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "map:classes", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Desabilita o mapeamento JPA da classe", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desabilita o mapeamento Lombok da classe", defaultValue = "false", arity = 0) Boolean noLombok,
			@ShellOption(help = "Desabilita o mapeamento Jackson da classe", defaultValue = "false", arity = 0) Boolean noJackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		EUmlClassDiagram umlClassDiagram = getDomainClassDiagram(bootProject);

		Collection<JavaClassSource> javaClassSources = new ArrayList<>();

		for (EUmlClass umlClass : umlClassDiagram.getClasses()) {
			JavaClassSource javaClassSource = umlClass.convertToJavaClassSource(bootProject);
			if (!noJpa) this.jpaVisitor(umlClassDiagram, umlClass, javaClassSource);
			javaClassSources.add(javaClassSource);
		}

		saveJavaClasses(bootProject, javaClassSources);
	}

	private EUmlClassDiagram getDomainClassDiagram(EBootProject bootProject) {
		String diagramNotFoundError = "O projeto não possui o diagram de classe em docs/diagrams/domain-class.md";
		return bootProject.getDomainClassDiagram().orElseThrow(() -> new IllegalArgumentException(diagramNotFoundError));
	}

	private void jpaVisitor(EUmlClassDiagram classDiagram, EUmlClass umlClass, JavaClassSource javaClassSource) {
		JpaVisitor jpaVisitor = new JpaVisitor(javaClassSource);
		jpaVisitor.visitClass(umlClass);
		for (EUmlField umlField : umlClass.getFields()) {
			if (umlField.isId()) jpaVisitor.visitIdField(umlField);
			if (umlField.isLong()) jpaVisitor.visitLongField(umlField);
			if (umlField.isByteArray()) jpaVisitor.visitByteArrayField(umlField);
			if (umlField.isBoolean()) jpaVisitor.visitBooleanField(umlField);
			if (umlField.isInteger()) jpaVisitor.visitIntegerField(umlField);
			if (umlField.isLocalDate()) jpaVisitor.visitLocalDateField(umlField);
			if (umlField.isLocalDateTime()) jpaVisitor.visitLocalDateTimeField(umlField);
			if (umlField.isBigDecimal()) jpaVisitor.visitBigDecimalField(umlField);
			if (umlField.isString()) jpaVisitor.visitStringField(umlField);
			for (EUmlFieldProperty umlFieldProperty : umlField.getProperties()) {
				if (umlFieldProperty.isNotNull()) jpaVisitor.visitNotNullProperty(umlFieldProperty);
				if (umlFieldProperty.isUnique()) jpaVisitor.visitUniqueProperty(umlFieldProperty);
				if (umlFieldProperty.isTransient()) jpaVisitor.visitTransientProperty(umlFieldProperty);
			}
		}
	}

	private void saveJavaClasses(EBootProject bootProject, Collection<JavaClassSource> javaClassSources) {
		// @formatter:off
		javaClassSources.stream()
			.map(javaClassSource -> new EJavaClassImpl(bootProject, javaClassSource))
			.forEach(javaClass -> this.bootService.save(bootProject.getMainSourceFolder(), javaClass));
		// @formatter:on
		print(bold(cyan(String.valueOf(javaClassSources.size()))), " classes mapeadas.");
	}
}
