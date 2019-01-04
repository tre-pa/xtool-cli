package br.xtool.core.implementation.impl;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jboss.forge.roaster.Roaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.xtool.core.implementation.SpringBootProject;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.representation.JavaSourceFolderRepresentation;
import br.xtool.core.representation.JavaTypeRepresentation;
import br.xtool.core.representation.JpaProjectionRepresentation;
import br.xtool.core.representation.PlantEnumRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.converter.PlantEnumToJavaEnumConverter;
import br.xtool.core.representation.impl.EJpaProjectionImpl;
import br.xtool.core.util.RoasterUtil;
import lombok.SneakyThrows;

@Service
@Deprecated
public class SpringBootProjectImpl implements SpringBootProject {

	@Autowired
	private ApplicationContext applicationContext;

	// @Autowired
	// private JavaxValidationVisitor javaxValidationVisitor;

	@Override
	@SneakyThrows
	public void createDirectory(SpringBootProjectRepresentation bootProject, Path path) {
		Path finalPath = bootProject.getPath().resolve(path);
		if (Files.notExists(finalPath)) {
			Files.createDirectories(finalPath);
			Files.createFile(finalPath.resolve(".gitkeep"));
		}
	}

//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.core.service.BootService#addSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
//	 */
//	@Override
//	public <T extends BootProjectSupport> void addSupport(SpringBootProjectRepresentation bootProject, Class<T> supportClass) {
//		BootProjectSupport support = this.applicationContext.getBean(supportClass);
//		support.apply(bootProject);
//		support.apply(bootProject.getApplicationProperties());
//		support.apply(bootProject.getPom());
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.core.service.BootService#hasSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
//	 */
//	@Override
//	public <T extends BootProjectSupport> boolean hasSupport(SpringBootProjectRepresentation bootProject, Class<T> supportClass) {
//		return this.applicationContext.getBean(supportClass).has(bootProject);
//	}

	// /*
	// * (non-Javadoc)
	// * @see
	// br.xtool.service.BootService#save(br.xtool.core.representation.EJavaSourceFolder,
	// br.xtool.core.representation.EJavaClass)
	// */
	// // @Override
	@SneakyThrows
	public void save(JavaSourceFolderRepresentation sourceFolder, JavaClassRepresentation javaClass) {
		Path javaPath = sourceFolder.getPath().resolve(javaClass.getJavaPackage().getDir()).resolve(String.format("%s.java", javaClass.getName()));
		if (Files.notExists(javaPath.getParent())) Files.createDirectories(javaPath.getParent());
		Properties prefs = new Properties();
		prefs.setProperty(JavaCore.COMPILER_SOURCE, CompilerOptions.VERSION_1_8);
		prefs.setProperty(JavaCore.COMPILER_COMPLIANCE, CompilerOptions.VERSION_1_8);
		prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, CompilerOptions.VERSION_1_8);
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "120");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD, "1");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_IMPORTS, "1");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE, "1");
		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_USE_ON_OFF_TAGS, DefaultCodeFormatterConstants.TRUE);

		// prefs.setProperty(DefaultCodeFormatterConstants., "TRUE");
		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
			String formatedJavaClassSource = Roaster.format(prefs, javaClass.getRoasterJavaClass().toUnformattedString());
			write.write(formatedJavaClassSource);
			write.flush();
			sourceFolder.getBootProject().refresh();
		}
	}
	//
	// /*
	// * (non-Javadoc)
	// * @see
	// br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaClass)
	// */
	// // @Override
	// public void save(EJavaClass javaClass) {
	// this.save(javaClass.getProject().getMainSourceFolder(), javaClass);
	// }
	//
	// /*
	// * (non-Javadoc)
	// * @see
	// br.xtool.service.BootService#save(br.xtool.core.representation.EJavaSourceFolder,
	// br.xtool.core.representation.EJavaInterface)
	// */
	// // @Override
	// @SneakyThrows
	// public void save(EJavaSourceFolder sourceFolder, EJavaInterface
	// javaInterface) {
	// Path javaPath =
	// sourceFolder.getPath().resolve(javaInterface.getJavaPackage().getDir()).resolve(String.format("%s.java",
	// javaInterface.getName()));
	// if (Files.notExists(javaPath.getParent()))
	// Files.createDirectories(javaPath.getParent());
	// Properties prefs = new Properties();
	// prefs.setProperty(JavaCore.COMPILER_SOURCE, CompilerOptions.VERSION_1_8);
	// prefs.setProperty(JavaCore.COMPILER_COMPLIANCE, CompilerOptions.VERSION_1_8);
	// prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
	// CompilerOptions.VERSION_1_8);
	// prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "120");
	// prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD,
	// "1");
	// prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_IMPORTS,
	// "1");
	// prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE,
	// "1");
	//
	// // prefs.setProperty(DefaultCodeFormatterConstants., "TRUE");
	// try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
	// String formatedJavaClassSource = Roaster.format(prefs,
	// javaInterface.getRoasterInterface().toUnformattedString());
	// write.write(formatedJavaClassSource);
	// write.flush();
	// sourceFolder.getBootProject().refresh();
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// * @see
	// br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaInterface)
	// */
	// // @Override
	// public void save(EJavaInterface javaInterface) {
	// this.save(javaInterface.getProject().getMainSourceFolder(), javaInterface);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.
	 * EJavaType)
	 */
//	@SneakyThrows
//	@Override
//	public void save(JavaTypeRepresentation<?> javaType) {
//		Path javaPath = javaType.getProject().getMainSourceFolder().getPath().resolve(javaType.getJavaPackage().getDir()).resolve(String.format("%s.java", javaType.getName()));
//		if (Files.notExists(javaPath.getParent())) Files.createDirectories(javaPath.getParent());
//		Properties prefs = new Properties();
//		prefs.setProperty(JavaCore.COMPILER_SOURCE, CompilerOptions.VERSION_1_8);
//		prefs.setProperty(JavaCore.COMPILER_COMPLIANCE, CompilerOptions.VERSION_1_8);
//		prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, CompilerOptions.VERSION_1_8);
//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "120");
//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD, "1");
//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_IMPORTS, "1");
//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE, "1");
//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_USE_ON_OFF_TAGS, DefaultCodeFormatterConstants.TRUE);
//		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
//			String formatedJavaClassSource = Roaster.format(prefs, javaType.toUnformattedString());
//			write.write(formatedJavaClassSource);
//			write.flush();
//			javaType.getProject().refresh();
//			ConsoleLog.print(ConsoleLog.cyan(" + "), ConsoleLog.white(javaType.getQualifiedName()));
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.
	 * EJavaType[])
	 */
	@Override
	public void save(JavaTypeRepresentation<?>... javaTypes) {
		Stream.of(javaTypes).forEach(this::save);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.service.BootService#convertUmlClassDiagramToJavaClasses(br.
	 * xtool.core.representation.EBootProject)
	 */
//	@Override
//	@Deprecated
//	public Collection<JavaClassRepresentation> umlClassesToJavaClasses(SpringBootProjectRepresentation bootProject, Set<Visitor> vistors) {
//		// @formatter:off
//		Collection<JavaClassRepresentation> javaClasses = bootProject.getDomainClassDiagram().getClasses().stream()
//				.map(umlClass -> convertUmlClassToJavaClass(bootProject, umlClass, vistors))
//				.collect(Collectors.toList());
//		// @formatter:on
//		javaClasses.stream().forEach(javaClass -> {
//			this.save(bootProject.getMainSourceFolder(), javaClass);
//			ConsoleLog.print(ConsoleLog.cyan(" + "), ConsoleLog.white(javaClass.getQualifiedName()));
//		});
////		print(bold(cyan(String.valueOf(javaClasses.size()))), " classes mapeadas.");
//		return javaClasses;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#umlEnumsToJavaEnums(br.xtool.core.
	 * representation.EBootProject, java.util.Set)
	 */
	@Override
	@Deprecated
	public Collection<JavaEnumRepresentation> umlEnumsToJavaEnums(SpringBootProjectRepresentation bootProject) {
		// @formatter:off
		Collection<JavaEnumRepresentation> javaEnums = bootProject.getDomainClassDiagram().getEnums().stream()
				.map(umlEnum -> this.convertUmlEnumToJavaEnum(bootProject, umlEnum))
				.collect(Collectors.toList());
		
		// @formatter:on
		javaEnums.stream().forEach(javaEnum -> {
			this.save(javaEnum);
		});
//		print(bold(cyan(String.valueOf(javaEnums.size()))), " enums mapeadas.");
		return javaEnums;
	}

//	// Converte uma classe UML para um objeto EJavaClass.
//	private JavaClassRepresentation convertUmlClassToJavaClass(SpringBootProjectRepresentation bootProject, PlantClassRepresentation umlClass,
//			Set<? extends Visitor> vistors) {
//		JavaClassRepresentation javaClass = new PlantClassRepresentationToJavaClassRepresentationConverter(vistors).apply(bootProject, umlClass);
//		umlClass.getFields().stream().forEach(umlField -> new PlantClassFieldToJavaClassConverter(vistors).apply(javaClass, umlField));
//		umlClass.getRelationships().stream().forEach(umlRelationship -> new PlantRelationshipConverter(vistors).apply(javaClass, umlRelationship));
//		return javaClass;
//	}

	private JavaEnumRepresentation convertUmlEnumToJavaEnum(SpringBootProjectRepresentation bootProject, PlantEnumRepresentation umlEnum) {
		return new PlantEnumToJavaEnumConverter().apply(bootProject, umlEnum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.service.BootService#createRepository(br.xtool.core.representation.
	 * EBootProject, br.xtool.core.representation.EJpaEntity)
	 */
//	@Override
//	public RepositoryRepresentation createRepository(SpringBootProjectRepresentation bootProject, EntityRepresentation entity) {
//		String repositoryName = entity.getName().concat("Repository");
//		// @formatter:off
//		return bootProject.getRepositories().stream()
//				.filter(repository -> repository.getName().equals(repositoryName))
//				.findFirst()
//				.orElseGet(() -> this.newRepository(bootProject, repositoryName, entity));
//		// @formatter:on
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#createRepository(br.xtool.core.
	 * representation.EBootProject, br.xtool.core.representation.EJpaEntity,
	 * java.util.function.Consumer)
	 */
//	@Override
//	public void createRepository(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<RepositoryRepresentation> consumer) {
//		RepositoryRepresentation repository = this.createRepository(bootProject, entity);
//		consumer.accept(repository);
//		this.save(repository);
//	}
//
//	private RepositoryRepresentation newRepository(SpringBootProjectRepresentation bootProject, String repositoryName, EntityRepresentation entity) {
//		RepositoryRepresentation repository = new RepositoryRepresentationImpl(bootProject, RoasterUtil.createJavaInterface(repositoryName));
//		repository.getRoasterInterface().setPackage(bootProject.getRootPackage().getName().concat(".repository"));
//		repository.getRoasterInterface().addImport(JpaRepository.class);
//		repository.getRoasterInterface().addImport(JpaSpecificationExecutor.class);
//		repository.getRoasterInterface().addImport(entity.getQualifiedName());
//		repository.getRoasterInterface().addInterface(JpaRepository.class.getSimpleName().concat("<").concat(entity.getName()).concat(",").concat("Long").concat(">"));
//		repository.getRoasterInterface().addInterface(JpaSpecificationExecutor.class.getSimpleName().concat("<").concat(entity.getName()).concat(">"));
//		repository.getRoasterInterface().addAnnotation(Repository.class);
//		return repository;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.service.BootService#createProjection(br.xtool.core.representation.
	 * EBootProject, br.xtool.core.representation.EJpaEntity)
	 */
	@Override
	public JpaProjectionRepresentation createProjection(SpringBootProjectRepresentation bootProject, EntityRepresentation entity) {
		String projectionName = entity.getName().concat("Projection");
		// @formatter:off
		return bootProject.getProjections().stream()
				.filter(projection -> projection.getName().equals(projectionName))
				.findFirst()
				.orElseGet(() -> this.newProjection(bootProject, projectionName, entity));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#createProjection(br.xtool.core.
	 * representation.EBootProject, br.xtool.core.representation.EJpaEntity,
	 * java.util.function.Consumer)
	 */
	@Override
	public void createProjection(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<JpaProjectionRepresentation> consumer) {
		JpaProjectionRepresentation projection = this.createProjection(bootProject, entity);
		consumer.accept(projection);
		this.save(projection);
	}

	private JpaProjectionRepresentation newProjection(SpringBootProjectRepresentation bootProject, String projectionName, EntityRepresentation entity) {
		JpaProjectionRepresentation projection = new EJpaProjectionImpl(bootProject, RoasterUtil.createJavaInterface(projectionName));
		projection.getRoasterInterface().setPackage(bootProject.getRootPackage().getName().concat(".domain").concat(".projection"));
		// @formatter:off
		entity.getJavaFields().stream()
			.filter(javaField -> !javaField.getRoasterField().isStatic())
			.filter(javaField -> !javaField.getRelationship().isPresent())
			.filter(javaField -> !javaField.isCollection())
			.forEach(javaField -> {
				projection.getRoasterInterface().addMethod()
					.setReturnType(javaField.getType())
					.setName(String.format("get%s", StringUtils.capitalize(javaField.getName())));
			});
		// @formatter:on
		return projection;
	}

//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.service.BootService#createSpecification(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity)
//	 */
//	@Override
//	@Deprecated
//	public SpecificationRepresentation createSpecification(SpringBootProjectRepresentation bootProject, EntityRepresentation entity) {
//		String specificationName = entity.getName().concat("Specification");
//		// @formatter:off
//		return bootProject.getSpecifications().stream()
//				.filter(specification -> specification.getName().equals(specificationName))
//				.findFirst()
//				.orElseGet(() -> this.newSpecification(bootProject, specificationName, entity));
//		// @formatter:on
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.service.BootProjectService#createSpecification(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity, java.util.function.Consumer)
//	 */
//	@Override
//	@Deprecated
//	public void createSpecification(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<SpecificationRepresentation> consumer) {
//		SpecificationRepresentation specification = this.createSpecification(bootProject, entity);
//		consumer.accept(specification);
//		this.save(specification);
//	}
//
//	private SpecificationRepresentation newSpecification(SpringBootProjectRepresentation bootProject, String specificationName, EntityRepresentation entity) {
//		SpecificationRepresentation specification = new EJpaSpecificationImpl(bootProject, RoasterUtil.createJavaClassSource(specificationName));
//		specification.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".repository").concat(".specification"));
//		return specification;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.BootProjectService#createService(br.xtool.core.
	 * representation.EBootProject, br.xtool.core.representation.EJpaRepository)
	 */
//	@Override
//	public ServiceClassRepresentation createService(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository) {
//		String serviceName = jpaRepository.getTargetEntity().getName().concat("Service");
//		// @formatter:off
//		return bootProject.getServices().stream()
//				.filter(service -> service.getName().equals(serviceName))
//				.findFirst()
//				.orElseGet(() -> this.newService(bootProject, serviceName, jpaRepository));
//		// @formatter:on
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.service.BootProjectService#createService(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository, java.util.function.Consumer)
//	 */
//	@Override
//	public void createService(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository, Consumer<ServiceClassRepresentation> consumer) {
//		ServiceClassRepresentation service = this.createService(bootProject, jpaRepository);
//		consumer.accept(service);
//		this.save(service);
//	}
//
//	private ServiceClassRepresentation newService(SpringBootProjectRepresentation bootProject, String serviceName, RepositoryRepresentation repository) {
//		ServiceClassRepresentation service = new EBootServiceImpl(bootProject, RoasterUtil.createJavaClassSource(serviceName));
//		service.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".service"));
//		service.getRoasterJavaClass().addImport(Autowired.class);
//		service.getRoasterJavaClass().addImport(repository.getQualifiedName());
//		service.getRoasterJavaClass().addAnnotation(Service.class);
//		// @formatter:off
//		service.getRoasterJavaClass().addField()
//			.setPrivate()
//			.setName(repository.getInstanceName())
//			.setType(repository)
//			.addAnnotation(Autowired.class);
//		// @formatter:on
//		return service;
//	}

//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.service.BootProjectService#createRest(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository)
//	 */
//	@Override
//	public RestClassRepresentation createRest(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository) {
//		String restName = jpaRepository.getTargetEntity().getName().concat("Rest");
//		// @formatter:off
//		return bootProject.getRests().stream()
//				.filter(rest -> rest.getName().equals(restName))
//				.findFirst()
//				.orElseGet(() -> this.newRest(bootProject, restName, jpaRepository));
//		// @formatter:on
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see br.xtool.service.BootProjectService#createRest(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository, java.util.function.Consumer)
//	 */
//	@Override
//	public void createRest(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository, Consumer<RestClassRepresentation> consumer) {
//		RestClassRepresentation rest = this.createRest(bootProject, jpaRepository);
//		consumer.accept(rest);
//		this.save(rest);
//	}
//
//	private RestClassRepresentation newRest(SpringBootProjectRepresentation bootProject, String restName, RepositoryRepresentation repository) {
//		RestClassRepresentation rest = new RestClassRepresentationImpl(bootProject, RoasterUtil.createJavaClassSource(restName));
//		rest.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".rest"));
//		rest.getRoasterJavaClass().addAnnotation(Slf4j.class);
//		rest.getRoasterJavaClass().addImport(Autowired.class);
//		rest.getRoasterJavaClass().addImport(repository.getQualifiedName());
//		rest.getRoasterJavaClass().addAnnotation(RestController.class);
//		// @formatter:off
//		rest.getRoasterJavaClass().addAnnotation(RequestMapping.class)
//			.setStringValue(String.format("api/%s",Inflector.getInstance().pluralize(Strman.toKebabCase(repository.getTargetEntity().getName()))));
//		rest.getRoasterJavaClass().addField()
//			.setPrivate()
//			.setName(repository.getInstanceName())
//			.setType(repository)
//			.addAnnotation(Autowired.class);
//		// @formatter:on
//		return rest;
//	}

}
