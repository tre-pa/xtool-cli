package br.xtool.core.service.impl;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EBootService;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaEnum;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.representation.EJavaType;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaProjection;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.representation.EJpaSpecification;
import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantEnum;
import br.xtool.core.representation.converter.EUmlRelationshipConverter;
import br.xtool.core.representation.converter.PlantClassFieldToJavaClassConverter;
import br.xtool.core.representation.converter.PlantClassToJavaClassConverter;
import br.xtool.core.representation.converter.PlantEnumToJavaEnumConverter;
import br.xtool.core.representation.impl.EBootRestImpl;
import br.xtool.core.representation.impl.EBootServiceImpl;
import br.xtool.core.representation.impl.EJpaProjectionImpl;
import br.xtool.core.representation.impl.EJpaRepositoryImpl;
import br.xtool.core.representation.impl.EJpaSpecificationImpl;
import br.xtool.core.util.Inflector;
import br.xtool.core.util.RoasterUtil;
import br.xtool.core.visitor.Visitor;
import br.xtool.service.BootProjectService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import strman.Strman;

@Service
public class BootProjectServiceImpl implements BootProjectService {

	@Autowired
	private ApplicationContext applicationContext;

	//	@Autowired
	//	private JavaxValidationVisitor javaxValidationVisitor;

	@Override
	@SneakyThrows
	public void createDirectory(EBootProject bootProject, Path path) {
		Path finalPath = bootProject.getPath().resolve(path);
		if (Files.notExists(finalPath)) {
			Files.createDirectories(finalPath);
			Files.createFile(finalPath.resolve(".gitkeep"));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#addSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootProjectSupport> void addSupport(EBootProject bootProject, Class<T> supportClass) {
		BootProjectSupport support = this.applicationContext.getBean(supportClass);
		support.apply(bootProject);
		support.apply(bootProject.getApplicationProperties());
		support.apply(bootProject.getPom());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#hasSupport(br.xtool.core.representation.EBootProject, java.lang.Class)
	 */
	@Override
	public <T extends BootProjectSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass) {
		return this.applicationContext.getBean(supportClass).has(bootProject);
	}

	//	/*
	//	 * (non-Javadoc)
	//	 * @see br.xtool.service.BootService#save(br.xtool.core.representation.EJavaSourceFolder, br.xtool.core.representation.EJavaClass)
	//	 */
	//	//	@Override
	@SneakyThrows
	public void save(EJavaSourceFolder sourceFolder, EJavaClass javaClass) {
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

		//		prefs.setProperty(DefaultCodeFormatterConstants., "TRUE");
		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
			String formatedJavaClassSource = Roaster.format(prefs, javaClass.getRoasterJavaClass().toUnformattedString());
			write.write(formatedJavaClassSource);
			write.flush();
			sourceFolder.getBootProject().refresh();
		}
	}
	//
	//	/*
	//	 * (non-Javadoc)
	//	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaClass)
	//	 */
	//	//	@Override
	//	public void save(EJavaClass javaClass) {
	//		this.save(javaClass.getProject().getMainSourceFolder(), javaClass);
	//	}
	//
	//	/*
	//	 * (non-Javadoc)
	//	 * @see br.xtool.service.BootService#save(br.xtool.core.representation.EJavaSourceFolder, br.xtool.core.representation.EJavaInterface)
	//	 */
	//	//	@Override
	//	@SneakyThrows
	//	public void save(EJavaSourceFolder sourceFolder, EJavaInterface javaInterface) {
	//		Path javaPath = sourceFolder.getPath().resolve(javaInterface.getJavaPackage().getDir()).resolve(String.format("%s.java", javaInterface.getName()));
	//		if (Files.notExists(javaPath.getParent())) Files.createDirectories(javaPath.getParent());
	//		Properties prefs = new Properties();
	//		prefs.setProperty(JavaCore.COMPILER_SOURCE, CompilerOptions.VERSION_1_8);
	//		prefs.setProperty(JavaCore.COMPILER_COMPLIANCE, CompilerOptions.VERSION_1_8);
	//		prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, CompilerOptions.VERSION_1_8);
	//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "120");
	//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD, "1");
	//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_IMPORTS, "1");
	//		prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE, "1");
	//
	//		//		prefs.setProperty(DefaultCodeFormatterConstants., "TRUE");
	//		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
	//			String formatedJavaClassSource = Roaster.format(prefs, javaInterface.getRoasterInterface().toUnformattedString());
	//			write.write(formatedJavaClassSource);
	//			write.flush();
	//			sourceFolder.getBootProject().refresh();
	//		}
	//	}
	//
	//	/*
	//	 * (non-Javadoc)
	//	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaInterface)
	//	 */
	//	//	@Override
	//	public void save(EJavaInterface javaInterface) {
	//		this.save(javaInterface.getProject().getMainSourceFolder(), javaInterface);
	//	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaType)
	 */
	@SneakyThrows
	@Override
	public void save(EJavaType<?> javaType) {
		Path javaPath = javaType.getProject().getMainSourceFolder().getPath().resolve(javaType.getJavaPackage().getDir()).resolve(String.format("%s.java", javaType.getName()));
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
		try (BufferedWriter write = Files.newBufferedWriter(javaPath)) {
			String formatedJavaClassSource = Roaster.format(prefs, javaType.toUnformattedString());
			write.write(formatedJavaClassSource);
			write.flush();
			javaType.getProject().refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#save(br.xtool.core.representation.EJavaType[])
	 */
	@Override
	public void save(EJavaType<?>... javaTypes) {
		Stream.of(javaTypes).forEach(this::save);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.BootService#convertUmlClassDiagramToJavaClasses(br.xtool.core.representation.EBootProject)
	 */
	@Override
	public Collection<EJavaClass> umlClassesToJavaClasses(EBootProject bootProject, Set<Visitor> vistors) {
		// @formatter:off
		Collection<EJavaClass> javaClasses = bootProject.getDomainClassDiagram().getClasses().stream()
				.map(umlClass -> convertUmlClassToJavaClass(bootProject, umlClass, vistors))
				.collect(Collectors.toList());
		// @formatter:on
		javaClasses.stream().forEach(javaClass -> this.save(bootProject.getMainSourceFolder(), javaClass));
//		print(bold(cyan(String.valueOf(javaClasses.size()))), " classes mapeadas.");
		return javaClasses;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#umlEnumsToJavaEnums(br.xtool.core.representation.EBootProject, java.util.Set)
	 */
	@Override
	public Collection<EJavaEnum> umlEnumsToJavaEnums(EBootProject bootProject) {
		// @formatter:off
		Collection<EJavaEnum> javaEnums = bootProject.getDomainClassDiagram().getEnums().stream()
				.map(umlEnum -> this.convertUmlEnumToJavaEnum(bootProject, umlEnum))
				.collect(Collectors.toList());
		// @formatter:on
		javaEnums.stream().forEach(javaEnum -> this.save(javaEnum));
//		print(bold(cyan(String.valueOf(javaEnums.size()))), " enums mapeadas.");
		return javaEnums;
	}

	// Converte uma classe UML para um objeto EJavaClass.
	private EJavaClass convertUmlClassToJavaClass(EBootProject bootProject, EPlantClass umlClass, Set<? extends Visitor> vistors) {
		EJavaClass javaClass = new PlantClassToJavaClassConverter(vistors).apply(bootProject, umlClass);
		umlClass.getFields().stream().forEach(umlField -> new PlantClassFieldToJavaClassConverter(vistors).apply(javaClass, umlField));
		umlClass.getRelationships().stream().forEach(umlRelationship -> new EUmlRelationshipConverter(vistors).apply(javaClass, umlRelationship));
		return javaClass;
	}

	private EJavaEnum convertUmlEnumToJavaEnum(EBootProject bootProject, EPlantEnum umlEnum) {
		return new PlantEnumToJavaEnumConverter().apply(bootProject, umlEnum);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootService#createRepository(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity)
	 */
	@Override
	public EJpaRepository createRepository(EBootProject bootProject, EJpaEntity entity) {
		String repositoryName = entity.getName().concat("Repository");
		// @formatter:off
		return bootProject.getRepositories().stream()
				.filter(repository -> repository.getName().equals(repositoryName))
				.findFirst()
				.orElseGet(() -> this.newRepository(bootProject, repositoryName, entity));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createRepository(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity, java.util.function.Consumer)
	 */
	@Override
	public void createRepository(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaRepository> consumer) {
		EJpaRepository repository = this.createRepository(bootProject, entity);
		consumer.accept(repository);
		this.save(repository);
	}

	private EJpaRepository newRepository(EBootProject bootProject, String repositoryName, EJpaEntity entity) {
		EJpaRepository repository = new EJpaRepositoryImpl(bootProject, RoasterUtil.createJavaInterface(repositoryName));
		repository.getRoasterInterface().setPackage(bootProject.getRootPackage().getName().concat(".repository"));
		repository.getRoasterInterface().addImport(JpaRepository.class);
		repository.getRoasterInterface().addImport(JpaSpecificationExecutor.class);
		repository.getRoasterInterface().addImport(entity.getQualifiedName());
		repository.getRoasterInterface().addInterface(JpaRepository.class.getSimpleName().concat("<").concat(entity.getName()).concat(",").concat("Long").concat(">"));
		repository.getRoasterInterface().addInterface(JpaSpecificationExecutor.class.getSimpleName().concat("<").concat(entity.getName()).concat(">"));
		repository.getRoasterInterface().addAnnotation(Repository.class);
		return repository;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootService#createProjection(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity)
	 */
	@Override
	public EJpaProjection createProjection(EBootProject bootProject, EJpaEntity entity) {
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
	 * @see br.xtool.service.BootProjectService#createProjection(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity, java.util.function.Consumer)
	 */
	@Override
	public void createProjection(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaProjection> consumer) {
		EJpaProjection projection = this.createProjection(bootProject, entity);
		consumer.accept(projection);
		this.save(projection);
	}

	private EJpaProjection newProjection(EBootProject bootProject, String projectionName, EJpaEntity entity) {
		EJpaProjection projection = new EJpaProjectionImpl(bootProject, RoasterUtil.createJavaInterface(projectionName));
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootService#createSpecification(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity)
	 */
	@Override
	public EJpaSpecification createSpecification(EBootProject bootProject, EJpaEntity entity) {
		String specificationName = entity.getName().concat("Specification");
		// @formatter:off
		return bootProject.getSpecifications().stream()
				.filter(specification -> specification.getName().equals(specificationName))
				.findFirst()
				.orElseGet(() -> this.newSpecification(bootProject, specificationName, entity));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createSpecification(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaEntity, java.util.function.Consumer)
	 */
	@Override
	public void createSpecification(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaSpecification> consumer) {
		EJpaSpecification specification = this.createSpecification(bootProject, entity);
		consumer.accept(specification);
		this.save(specification);
	}

	private EJpaSpecification newSpecification(EBootProject bootProject, String specificationName, EJpaEntity entity) {
		EJpaSpecification specification = new EJpaSpecificationImpl(bootProject, RoasterUtil.createJavaClassSource(specificationName));
		specification.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".repository").concat(".specification"));
		return specification;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createService(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository)
	 */
	@Override
	public EBootService createService(EBootProject bootProject, EJpaRepository jpaRepository) {
		String serviceName = jpaRepository.getTargetEntity().getName().concat("Service");
		// @formatter:off
		return bootProject.getServices().stream()
				.filter(service -> service.getName().equals(serviceName))
				.findFirst()
				.orElseGet(() -> this.newService(bootProject, serviceName, jpaRepository));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createService(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository, java.util.function.Consumer)
	 */
	@Override
	public void createService(EBootProject bootProject, EJpaRepository jpaRepository, Consumer<EBootService> consumer) {
		EBootService service = this.createService(bootProject, jpaRepository);
		consumer.accept(service);
		this.save(service);
	}

	private EBootService newService(EBootProject bootProject, String serviceName, EJpaRepository repository) {
		EBootService service = new EBootServiceImpl(bootProject, RoasterUtil.createJavaClassSource(serviceName));
		service.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".service"));
		service.getRoasterJavaClass().addImport(Autowired.class);
		service.getRoasterJavaClass().addImport(repository.getQualifiedName());
		service.getRoasterJavaClass().addAnnotation(Service.class);
		// @formatter:off
		service.getRoasterJavaClass().addField()
			.setPrivate()
			.setName(repository.getInstanceName())
			.setType(repository)
			.addAnnotation(Autowired.class);
		// @formatter:on
		return service;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createRest(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository)
	 */
	@Override
	public EBootRest createRest(EBootProject bootProject, EJpaRepository jpaRepository) {
		String restName = jpaRepository.getTargetEntity().getName().concat("Rest");
		// @formatter:off
		return bootProject.getRests().stream()
				.filter(rest -> rest.getName().equals(restName))
				.findFirst()
				.orElseGet(() -> this.newRest(bootProject, restName, jpaRepository));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.BootProjectService#createRest(br.xtool.core.representation.EBootProject, br.xtool.core.representation.EJpaRepository, java.util.function.Consumer)
	 */
	@Override
	public void createRest(EBootProject bootProject, EJpaRepository jpaRepository, Consumer<EBootRest> consumer) {
		EBootRest rest = this.createRest(bootProject, jpaRepository);
		consumer.accept(rest);
		this.save(rest);
	}

	private EBootRest newRest(EBootProject bootProject, String restName, EJpaRepository repository) {
		EBootRest rest = new EBootRestImpl(bootProject, RoasterUtil.createJavaClassSource(restName));
		rest.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".rest"));
		rest.getRoasterJavaClass().addAnnotation(Slf4j.class);
		rest.getRoasterJavaClass().addImport(Autowired.class);
		rest.getRoasterJavaClass().addImport(repository.getQualifiedName());
		rest.getRoasterJavaClass().addAnnotation(RestController.class);
		// @formatter:off
		rest.getRoasterJavaClass().addAnnotation(RequestMapping.class)
			.setStringValue(String.format("api/%s",Inflector.getInstance().pluralize(Strman.toKebabCase(repository.getTargetEntity().getName()))));
		rest.getRoasterJavaClass().addField()
			.setPrivate()
			.setName(repository.getInstanceName())
			.setType(repository)
			.addAnnotation(Autowired.class);
		// @formatter:on
		return rest;
	}

}
