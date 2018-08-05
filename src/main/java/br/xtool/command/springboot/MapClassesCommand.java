package br.xtool.command.springboot;

import static br.xtool.core.ConsoleLog.bold;
import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.service.BootService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.visitor.Visitor;
import br.xtool.core.visitor.impl.JavaxValidationVisitor;

@ShellComponent
public class MapClassesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@Autowired
	private ApplicationContext applicationContext;
	//
	//	@Autowired
	//	private JpaClassVisitor jpaClassVisitor;

	@ShellMethod(key = "map:classes", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Desabilita o mapeamento JPA da classe", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desabilita o mapeamento Lombok da classe", defaultValue = "false", arity = 0) Boolean noLombok,
			@ShellOption(help = "Desabilita o mapeamento Jackson da classe", defaultValue = "false", arity = 0) Boolean noJackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		//EUmlClassDiagram umlClassDiagram = getDomainClassDiagram(bootProject);

		Set<Visitor> visitors = new HashSet<>();
		visitors.add(this.applicationContext.getBean(JavaxValidationVisitor.class));

		this.bootService.convertUmlClassDiagramToJavaClasses(bootProject, visitors);
		//		Collection<EJavaClass> javaClasses = new ArrayList<>();

		//
		//		for (EUmlClass umlClass : umlClassDiagram.getClasses()) {
		//			EJavaClass javaClass = umlClass.convertToJavaClass(bootProject);
		//			if (!noJpa) this.jpaVisitor(umlClassDiagram, umlClass, javaClass);
		//			javaClasses.add(javaClass);
		//		}

		//		saveJavaClasses(bootProject, javaClasses);
	}

	//	private EUmlClassDiagram getDomainClassDiagram(EBootProject bootProject) {
	//		String diagramNotFoundError = "O projeto não possui o diagram de classe em docs/diagrams/domain-class.md";
	//		return bootProject.getDomainClassDiagram().orElseThrow(() -> new IllegalArgumentException(diagramNotFoundError));
	//	}

	private void jpaVisitor(EUmlClassDiagram classDiagram, EUmlClass umlClass, EJavaClass javaClass) {
		//		this.jpaClassVisitor.accept(javaClass, umlClass);

		//		for (EUmlField umlField : umlClass.getFields()) {
		//			for (EJavaField javaField : javaClass.getFields()) {
//				// @formatter:off
//				this.jpaFieldVisitors.stream()
//					.filter(visitor -> visitor.test(umlField))
//					.forEach(visitor -> visitor.accept(javaField, umlField));
//				// @formatter:on
		//			}
		//		}
		//		JpaVisitor jpaVisitor = new JpaVisitor(javaClassSource);
		//		jpaVisitor.visitClass(umlClass);
		//		for (EUmlField umlField : umlClass.getFields()) {
		//			if (umlField.isId()) jpaVisitor.visitIdField(umlField);
		//			if (umlField.isLong()) jpaVisitor.visitLongField(umlField);
		//			if (umlField.isByteArray()) jpaVisitor.visitByteArrayField(umlField);
		//			if (umlField.isBoolean()) jpaVisitor.visitBooleanField(umlField);
		//			if (umlField.isInteger()) jpaVisitor.visitIntegerField(umlField);
		//			if (umlField.isLocalDate()) jpaVisitor.visitLocalDateField(umlField);
		//			if (umlField.isLocalDateTime()) jpaVisitor.visitLocalDateTimeField(umlField);
		//			if (umlField.isBigDecimal()) jpaVisitor.visitBigDecimalField(umlField);
		//			if (umlField.isString()) jpaVisitor.visitStringField(umlField);
		//			for (EUmlFieldProperty umlFieldProperty : umlField.getProperties()) {
		//				if (umlFieldProperty.isNotNull()) jpaVisitor.visitNotNullProperty(umlFieldProperty);
		//				if (umlFieldProperty.isUnique()) jpaVisitor.visitUniqueProperty(umlFieldProperty);
		//				if (umlFieldProperty.isTransient()) jpaVisitor.visitTransientProperty(umlFieldProperty);
		//			}
		//		}
	}

	private void saveJavaClasses(EBootProject bootProject, Collection<EJavaClass> javaClasses) {
		javaClasses.stream().forEach(javaClass -> this.bootService.save(bootProject.getMainSourceFolder(), javaClass));
		print(bold(cyan(String.valueOf(javaClasses.size()))), " classes mapeadas.");
	}
}
