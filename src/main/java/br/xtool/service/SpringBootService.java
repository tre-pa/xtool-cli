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
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.Workspace;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.JavaPackageRepresentation;
import br.xtool.core.representation.JavaTypeRepresentation;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.RepositoryRepresentation;
import br.xtool.core.representation.RestClassRepresentation;
import br.xtool.core.representation.ServiceClassRepresentation;
import br.xtool.core.representation.SpecificationRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.impl.EJavaPackageImpl;
import br.xtool.core.template.RepositoryTemplates;
import br.xtool.core.template.RestClassTemplates;
import br.xtool.core.template.ServiceClassTemplates;
import br.xtool.core.template.SpecificationTemplates;
import lombok.SneakyThrows;
import strman.Strman;

@Service
public class SpringBootService {

	@Autowired
	private Workspace workspaceService;

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
		String packageName = JavaPackageRepresentation.getDefaultPrefix().concat(".")
				.concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
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
		SpringBootProjectRepresentation bootProject = this.workspaceService.createProject(
				SpringBootProjectRepresentation.class, 
				ProjectRepresentation.Type.SPRINGBOOT, 
				genProjectName(name), 
				ProjectRepresentation.Version.V1, 
				vars);
		// @formatter:on

		this.workspaceService.setWorkingProject(bootProject);
	}

	/**
	 * Cria uma inteface de Repository no projeto para a entidade.
	 * 
	 * @param springBootProject
	 * @param entity
	 * @return
	 */
	public RepositoryRepresentation genRepository(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
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
	 * @param springBootProject
	 * @param entity
	 * @return
	 */
	public SpecificationRepresentation genSpecification(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
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
	 * @param bootProject Projeto Spring Boot alvo.
	 * @param repository  Repositório selecionado.
	 * @return
	 */
	public ServiceClassRepresentation genService(SpringBootProjectRepresentation bootProject, RepositoryRepresentation repository) {
		String serviceName = repository.getTargetEntity().getName().concat("Service");
		// @formatter:off
		ServiceClassRepresentation serviceClass = bootProject.getServices().stream()
				.filter(service -> service.getName().equals(serviceName))
				.findFirst()
				.orElseGet(() -> ServiceClassTemplates.newServiceClassRepresentation(bootProject, repository));
		this.save(serviceClass);
		return serviceClass;
	}

	/**
	 * Cria uma classe Rest no projeto.
	 * 
	 * @param bootProject Projecto Spring Boot alvo.
	 * @param repository  Repositório selecionado.
	 * @return
	 */
	public RestClassRepresentation genRest(SpringBootProjectRepresentation bootProject, RepositoryRepresentation repository) {
		String restName = repository.getTargetEntity().getName().concat("Rest");
		// @formatter:off
		RestClassRepresentation restClass = bootProject.getRests().stream()
				.filter(rest -> rest.getName().equals(restName))
				.findFirst()
				.orElseGet(() -> RestClassTemplates.newRestClassRepresentation(bootProject, repository));
		// @formatter:on
		RestClassTemplates.genFindAll(restClass, repository);
		RestClassTemplates.genFilter(restClass, repository);
		RestClassTemplates.genFindById(restClass, repository);
		RestClassTemplates.genInsertMethod(restClass, repository);
		RestClassTemplates.genUpdateMethod(restClass, repository);
		RestClassTemplates.genDeleteMethod(restClass, repository);
		this.save(restClass);
		return restClass;
	}

	@SneakyThrows
	private void save(JavaTypeRepresentation<?> javaType) {
		Path javaPath = javaType.getProject().getMainSourceFolder().getPath().resolve(javaType.getJavaPackage().getDir())
				.resolve(String.format("%s.java", javaType.getName()));
		if (Files.notExists(javaPath.getParent()))
			Files.createDirectories(javaPath.getParent());
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
