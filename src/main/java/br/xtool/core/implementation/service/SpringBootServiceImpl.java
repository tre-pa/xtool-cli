package br.xtool.core.implementation.service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jboss.forge.roaster.Roaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.Clog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.implementation.representation.JavaPackageRepresentationImpl;
import br.xtool.core.pdiagram.map.JavaClassRepresentationMapper;
import br.xtool.core.pdiagram.map.JavaEnumRepresentationMapper;
import br.xtool.core.pdiagram.map.JavaFieldRepresentationMapper;
import br.xtool.core.pdiagram.map.JavaRelationshipRepresentationMapper;
import br.xtool.core.representation.ProjectRepresentation;
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
import br.xtool.service.SpringBootService;
import lombok.SneakyThrows;
import strman.Strman;

@Service
@Lazy
public class SpringBootServiceImpl implements SpringBootService {

	@Autowired
	private Workspace workspace;

	@Autowired
	private Shell shellService;

	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	private RepositoryTemplates repositoryTemplates;

	@Autowired
	private RestClassTemplates restClassTemplates;

	@Autowired
	private ServiceClassTemplates serviceClassTemplates;

	@Autowired
	private SpecificationTemplates specificationTemplates;

	/**
	 * Cria uma nova aplicação Spring Boot.
	 * 
	 * @param name Nome do projeto Spring Boot.
	 */
	@Override
	public SpringBootProjectRepresentation newApp(String name, String description, String version) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("projectName", genProjectName(name));
		vars.put("baseClassName", genBaseClassName(name));
		vars.put("rootPackage", genRootPackage(name));
		vars.put("clientSecret", UUID.randomUUID());
		vars.put("projectDesc", description);
		// @formatter:off
		SpringBootProjectRepresentation bootProject = this.workspace.createProject(
				ProjectRepresentation.Type.SPRINGBOOT, 
				version,
				genProjectName(name), 
				vars);
		// @formatter:on

		this.workspace.setWorkingProject(bootProject);
		this.shellService.runCmd(bootProject.getPath(), "chmod +x scripts/keycloak/register-client.sh");
		this.shellService.runCmd(bootProject.getPath(), "git init > /dev/null 2>&1 ");
		this.shellService.runCmd(bootProject.getPath(), "git add . > /dev/null 2>&1");
		this.shellService.runCmd(bootProject.getPath(), "git commit -m \"Inicial commit\" > /dev/null 2>&1 ");
		Clog.print(Clog.cyan("\t-- Commit inicial realizado no git. --"));

		return bootProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genEntity(br.xtool.core.representation. plantuml.PlantClassRepresentation)
	 */
	@Override
	public EntityRepresentation genEntity(PlantClassRepresentation plantClass) {
		Clog.printv(Clog.green("[CLASS] "), plantClass.getName(), " / ", plantClass.getRelationships().size(), " relationships");

		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		JavaClassRepresentation javaClass = appCtx.getBean(JavaClassRepresentationMapper.class).apply(plantClass);
		plantClass.getFields().stream().filter(PlantClassFieldRepresentation::isEnum).forEach(plantClassField -> {
			JavaEnumRepresentation javaEnum = appCtx.getBean(JavaEnumRepresentationMapper.class).apply(plantClassField.getPlantEnumRepresentation().get());
			save(javaEnum);
		});
		plantClass.getFields().stream().forEach(plantField -> appCtx.getBean(JavaFieldRepresentationMapper.class).apply(javaClass, plantField));

//		System.out.println("\n\n");
//		System.out.println(String.format("Classe %s, Quantidade de Relacionamentos: %d", plantClass.getName(), plantClass.getRelationships().size()));
//		plantClass.getRelationships().forEach(r -> {
//			System.out.println(String.format("Source: %s, Target: %s", r.getSourceClass().getName(), r.getTargetClass().getName()));
//		});
		
		plantClass.getRelationships().stream().forEach(plantRelationship -> appCtx.getBean(JavaRelationshipRepresentationMapper.class).apply(javaClass, plantRelationship));
		save(javaClass);

		springBootProject.refresh();
		Clog.printv("");
		return new EntityRepresentationImpl(springBootProject, javaClass.getRoasterJavaClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genRepository(br.xtool.core.representation .springboot.EntityRepresentation)
	 */
	@Override
	public RepositoryRepresentation genRepository(EntityRepresentation entity) {
		entity.getAssociatedSpecification().orElseGet(() -> genSpecification(entity));
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String repositoryName = entity.getName().concat("Repository");
		// @formatter:off
		RepositoryRepresentation repository = springBootProject.getRepositories().stream()
				.filter(pRepository -> pRepository.getName().equals(repositoryName))
				.findFirst()
				.orElseGet(() -> repositoryTemplates.newRepositoryRepresentation(springBootProject, entity));
		// @formatter:on
		this.save(repository);
		springBootProject.refresh();
		return repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genSpecification(br.xtool.core. representation.springboot.EntityRepresentation)
	 */
	@Override
	public SpecificationRepresentation genSpecification(EntityRepresentation entity) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String specificationName = entity.getName().concat("Specification");
		// @formatter:off
		SpecificationRepresentation specification = springBootProject.getSpecifications().stream()
				.filter(pSpecification -> pSpecification.getName().equals(specificationName))
				.findFirst()
				.orElseGet(() -> specificationTemplates.newSpecificationRepresentation(springBootProject, entity));
		this.save(specification);
		return specification;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.SpringBootService#genService(br.xtool.core.representation.springboot.EntityRepresentation)
	 */
	@Override
	public ServiceClassRepresentation genService(EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(entity));
		
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String serviceName = entity.getName().concat("Service");
		// @formatter:off
		ServiceClassRepresentation serviceClass = springBootProject.getServices().stream()
				.filter(service -> service.getName().equals(serviceName))
				.findFirst()
				.orElseGet(() -> serviceClassTemplates.newServiceClassRepresentation(springBootProject, repository));
		this.save(serviceClass);
		springBootProject.refresh();
		return serviceClass;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.SpringBootService#genRest(br.xtool.core.representation.springboot.EntityRepresentation)
	 */
	@Override
	public RestClassRepresentation genRest(EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(entity));
		
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String restName = entity.getName().concat("Rest");
		// @formatter:off
		RestClassRepresentation restClass = springBootProject.getRests().stream()
				.filter(rest -> rest.getName().equals(restName))
				.findFirst()
				.orElseGet(() -> restClassTemplates.newRestClassRepresentation(springBootProject, repository));
		// @formatter:on
		this.save(restClass);
		springBootProject.refresh();
		return restClass;
	}

	@Override
	public void printEntities(SpringBootProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Entidades(%d) --", project.getEntities().size())));
		// @formatter:off
		project.getEntities()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on
	}

	@Override
	public void printRepositories(SpringBootProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Repositories(%d) --", project.getRepositories().size())));
		// @formatter:off
		project.getRepositories()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on

	}

	@Override
	public void printServices(SpringBootProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Services(%d) --", project.getServices().size())));
		// @formatter:off
		project.getServices()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on
	}

	@Override
	public void printRests(SpringBootProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Rests(%d) --", project.getRests().size())));
		// @formatter:off
		project.getRests()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genProjectName(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genBaseClassName(java.lang.String)
	 */
	@Override
	public String genBaseClassName(String projectName) {
		return Strman.toStudlyCase(projectName.endsWith("Application") ? projectName.replace("Application", "") : projectName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genRootPackage(java.lang.String)
	 */
	@Override
	public JavaPackageRepresentation genRootPackage(String projectName) {
		String packageName = JavaPackageRepresentation.getDefaultPrefix().concat(".").concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
		return JavaPackageRepresentationImpl.of(packageName);
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
			Clog.print(Clog.cyan(" + "), Clog.white(javaType.getQualifiedName()));
		}
	}

}
