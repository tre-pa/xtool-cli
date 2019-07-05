package br.xtool.core.implementation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.Clog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.helper.JavaTypeHelper;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
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
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.template.springboot.RepositoryTemplates;
import br.xtool.core.template.springboot.RestClassTemplates;
import br.xtool.core.template.springboot.ServiceClassTemplates;
import br.xtool.core.template.springboot.SpecificationTemplates;
import br.xtool.service.SpringBootProjectService;

@Service
@Lazy
public class SpringBootProjectServiceImpl implements SpringBootProjectService {

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
		vars.put("projectName", SpringBootProjectRepresentation.genProjectName(name));
		vars.put("projectDesc", description);
		vars.put("baseClassName", SpringBootProjectRepresentation.genBaseClassName(name));
		vars.put("rootPackage", SpringBootProjectRepresentation.genRootPackage(name));
		vars.put("clientSecret", UUID.randomUUID());
		// @formatter:off
		SpringBootProjectRepresentation bootProject = this.workspace.createProject(
				ProjectRepresentation.Type.SPRINGBOOT, 
				version,
				SpringBootProjectRepresentation.genProjectName(name), 
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
	public EntityRepresentation genEntity(SpringBootProjectRepresentation springBootProject, PlantClassRepresentation plantClass) {

		Clog.printv(Clog.green("[CLASS] "), plantClass.getName(), " / ", plantClass.getRelationships().size(), " relationships");

//		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		JavaClassRepresentation javaClass = appCtx.getBean(JavaClassRepresentationMapper.class).apply(springBootProject, plantClass);
		plantClass.getFields().stream().filter(PlantClassFieldRepresentation::isEnum).forEach(plantClassField -> {
			JavaEnumRepresentation javaEnum = appCtx.getBean(JavaEnumRepresentationMapper.class).apply(springBootProject, plantClassField.getPlantEnumRepresentation().get());
			JavaTypeHelper.save(javaEnum);
		});
		plantClass.getFields().stream().forEach(plantField -> appCtx.getBean(JavaFieldRepresentationMapper.class).apply(javaClass, plantField));

//		System.out.println("\n\n");
//		System.out.println(String.format("Classe %s, Quantidade de Relacionamentos: %d", plantClass.getName(), plantClass.getRelationships().size()));
//		plantClass.getRelationships().forEach(r -> {
//			System.out.println(String.format("Source: %s, Target: %s", r.getSourceClass().getName(), r.getTargetClass().getName()));
//		});

		plantClass.getRelationships().stream().forEach(plantRelationship -> appCtx.getBean(JavaRelationshipRepresentationMapper.class).apply(javaClass, plantRelationship));
		JavaTypeHelper.save(javaClass);

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
	public RepositoryRepresentation genRepository(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
		entity.getAssociatedSpecification().orElseGet(() -> genSpecification(springBootProject, entity));
//		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String repositoryName = entity.getName().concat("Repository");
		// @formatter:off
		RepositoryRepresentation repository = springBootProject.getRepositories().stream()
				.filter(pRepository -> pRepository.getName().equals(repositoryName))
				.findFirst()
				.orElseGet(() -> repositoryTemplates.newRepositoryRepresentation(springBootProject, entity));
		// @formatter:on
		JavaTypeHelper.save(repository);
		springBootProject.refresh();
		return repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.SpringBootService#genSpecification(br.xtool.core. representation.springboot.EntityRepresentation)
	 */
	@Override
	public SpecificationRepresentation genSpecification(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
//		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String specificationName = entity.getName().concat("Specification");
		// @formatter:off
		SpecificationRepresentation specification = springBootProject.getSpecifications().stream()
				.filter(pSpecification -> pSpecification.getName().equals(specificationName))
				.findFirst()
				.orElseGet(() -> specificationTemplates.newSpecificationRepresentation(springBootProject, entity));
		JavaTypeHelper.save(specification);
		return specification;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.SpringBootService#genService(br.xtool.core.representation.springboot.EntityRepresentation)
	 */
	@Override
	public ServiceClassRepresentation genService(SpringBootProjectRepresentation springBootProject,EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(springBootProject,entity));
		
//		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String serviceName = entity.getName().concat("Service");
		// @formatter:off
		ServiceClassRepresentation serviceClass = springBootProject.getServices().stream()
				.filter(service -> service.getName().equals(serviceName))
				.findFirst()
				.orElseGet(() -> serviceClassTemplates.newServiceClassRepresentation(springBootProject, repository));
		JavaTypeHelper.save(serviceClass);
		springBootProject.refresh();
		return serviceClass;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.service.SpringBootService#genRest(br.xtool.core.representation.springboot.EntityRepresentation)
	 */
	@Override
	public RestClassRepresentation genRest(SpringBootProjectRepresentation springBootProject,EntityRepresentation entity) {
		
		RepositoryRepresentation repository = entity.getAssociatedRepository().orElseGet(() -> this.genRepository(springBootProject,entity));
		
//		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		String restName = entity.getName().concat("Rest");
		// @formatter:off
		RestClassRepresentation restClass = springBootProject.getRests().stream()
				.filter(rest -> rest.getName().equals(restName))
				.findFirst()
				.orElseGet(() -> restClassTemplates.newRestClassRepresentation(springBootProject, repository));
		// @formatter:on
		JavaTypeHelper.save(restClass);
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

}
