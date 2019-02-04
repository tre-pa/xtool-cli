package br.xtool.service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jboss.forge.roaster.Roaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.converter.JavaClassRepresentationConverter;
import br.xtool.core.converter.JavaEnumRepresentationConverter;
import br.xtool.core.converter.JavaFieldRepresentationConverter;
import br.xtool.core.converter.JavaRelationshipRepresentationConverter;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.impl.EJavaPackageImpl;
import br.xtool.core.representation.impl.EntityRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.JavaPackageRepresentation;
import br.xtool.core.representation.springboot.JavaTypeRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.template.RepositoryTemplates;
import br.xtool.core.template.RestClassTemplates;
import br.xtool.core.template.ServiceClassTemplates;
import br.xtool.core.template.SpecificationTemplates;
import lombok.SneakyThrows;
import strman.Strman;

@Service
@Lazy
public class SpringBootService {

	@Autowired
	private Workspace workspace;
	
	@Autowired
	private Shell shellService;

	@Autowired
	private ApplicationContext appCtx;

	/**
	 * Gera um nome de projeto válido.
	 * 
	 * @param commomName
	 * @return
	 */
	public String genProjectName(String commomName) {
		// @formatter:off
		return StringUtils.lowerCase(
				Strman.toKebabCase(
					StringUtils.endsWithIgnoreCase(commomName, "-service") ? 
						commomName : 
						commomName.concat("-service")
						)
				);
		// @formatter:on
	}

	/**
	 * Gera um nome de classe base (Classe main) valido.
	 * 
	 * @param projectName
	 * @return
	 */
	public String genBaseClassName(String projectName) {
		return Strman.toStudlyCase(projectName.endsWith("Application") ? projectName.replace("Application", "") : projectName);

	}

	/**
	 * Gera um nome de pacote base válido.
	 * 
	 * @param projectName
	 * @return
	 */
	public JavaPackageRepresentation genRootPackage(String projectName) {
		String packageName = JavaPackageRepresentation.getDefaultPrefix().concat(".").concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
		return EJavaPackageImpl.of(packageName);
	}

	/**
	 * Cria uma nova aplicação Spring Boot.
	 * 
	 * @param name Nome do projeto Spring Boot.
	 */
	public void newApp(String name) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("projectName", genProjectName(name));
		vars.put("baseClassName", genBaseClassName(name));
		vars.put("rootPackage", genRootPackage(name));
		// @formatter:off
		SpringBootProjectRepresentation bootProject = this.workspace.createProject(
				ProjectRepresentation.Type.SPRINGBOOT, 
				genProjectName(name), 
				vars);
		// @formatter:on

		this.workspace.setWorkingProject(bootProject);
		this.shellService.runCmd(bootProject.getPath(), "git init");
		this.shellService.runCmd(bootProject.getPath(), "git add .");
		this.shellService.runCmd(bootProject.getPath(), "git commit -m \"Inicial commit\" ");
	}

	public EntityRepresentation genEntity(PlantClassRepresentation plantClass) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		JavaClassRepresentation javaClass = appCtx.getBean(JavaClassRepresentationConverter.class).apply(plantClass);
		plantClass.getFields().stream().forEach(plantField -> appCtx.getBean(JavaFieldRepresentationConverter.class).apply(javaClass, plantField));
		plantClass.getRelationships().stream().forEach(plantRelationship -> appCtx.getBean(JavaRelationshipRepresentationConverter.class).apply(javaClass, plantRelationship));
		plantClass.getFields().stream().filter(PlantClassFieldRepresentation::isEnum).forEach(plantClassField -> {
			JavaEnumRepresentation javaEnum = appCtx.getBean(JavaEnumRepresentationConverter.class).apply(plantClassField.getEnumRepresentation().get());
			save(javaEnum);
		});
		save(javaClass);
//		// Gera as classes dos relacionamento associados a classe.
//		plantClass.getRelationships().stream().forEach(plantRelationship -> {
//			if (springBootProject.getEntities().stream().noneMatch(entity -> entity.getName().equals(plantRelationship.getTargetClass().getName()))) {
//				genEntity(plantRelationship.getTargetClass());
//			}
//		});
		springBootProject.refresh();
		return new EntityRepresentationImpl(springBootProject, javaClass.getRoasterJavaClass());
	}

	/**
	 * Cria uma inteface de Repository no projeto para a entidade.
	 * 
	 * @param entity
	 * @return
	 */
	public RepositoryRepresentation genRepository(EntityRepresentation entity) {
		entity.getAssociatedSpecification().orElseGet(() -> genSpecification(entity));
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String repositoryName = entity.getName().concat("Repository");
		// @formatter:off
		RepositoryRepresentation repository = springBootProject.getRepositories().stream()
				.filter(pRepository -> pRepository.getName().equals(repositoryName))
				.findFirst()
				.orElseGet(() -> RepositoryTemplates.newRepositoryRepresentation(springBootProject, entity));
		// @formatter:on
		this.save(repository);
		return repository;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public SpecificationRepresentation genSpecification(EntityRepresentation entity) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String specificationName = entity.getName().concat("Specification");
		// @formatter:off
		SpecificationRepresentation specification = springBootProject.getSpecifications().stream()
				.filter(pSpecification -> pSpecification.getName().equals(specificationName))
				.findFirst()
				.orElseGet(() -> SpecificationTemplates.newSpecificationRepresentation(springBootProject, entity));
		this.save(specification);
		return specification;
	}

	/**
	 * Cria uma classe de Service no projeto.
	 * 
	 * @param repository  Repositório selecionado.
	 * @return
	 */
	public ServiceClassRepresentation genService(EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(entity));
		
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String serviceName = entity.getName().concat("Service");
		// @formatter:off
		ServiceClassRepresentation serviceClass = springBootProject.getServices().stream()
				.filter(service -> service.getName().equals(serviceName))
				.findFirst()
				.orElseGet(() -> ServiceClassTemplates.newServiceClassRepresentation(springBootProject, repository));
		this.save(serviceClass);
		return serviceClass;
	}

	/**
	 * Cria uma classe Rest no projeto.
	 * 
	 * @param repository  Repositório selecionado.
	 * @return
	 */
	public RestClassRepresentation genRest(EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(entity));
		
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String restName = entity.getName().concat("Rest");
		// @formatter:off
		RestClassRepresentation restClass = springBootProject.getRests().stream()
				.filter(rest -> rest.getName().equals(restName))
				.findFirst()
				.orElseGet(() -> RestClassTemplates.newRestClassRepresentation(springBootProject, repository));
		// @formatter:on
		this.save(restClass);
		return restClass;
	}

	@SneakyThrows
	private void save(JavaTypeRepresentation<?> javaType) {
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
			ConsoleLog.print(ConsoleLog.cyan(" + "), ConsoleLog.white(javaType.getQualifiedName()));
		}
	}

}
