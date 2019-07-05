package br.xtool.core.implementation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.SpringBootNgProjectService;

public class SpringBootNgProjectServiceImpl implements SpringBootNgProjectService {

	@Autowired
	private Workspace workspace;

	@Autowired
	private Shell shell;

	@Autowired
	private ApplicationContext appCtx;

	@Override
	public SpringBootNgProjectRepresentation newApp(String name, String description, String version) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("projectName", SpringBootNgProjectRepresentation.genProjectName(name));
		vars.put("projectDesc", description);
		vars.put("baseClassName", SpringBootNgProjectRepresentation.genBaseClassName(name));
		vars.put("rootPackage", SpringBootNgProjectRepresentation.genRootPackage(name));
		vars.put("clientSecret", UUID.randomUUID());
		// @formatter:off
		SpringBootNgProjectRepresentation bootProject = this.workspace.createProject(
				ProjectRepresentation.Type.SPRINGBOOTNG, 
				version,
				SpringBootNgProjectRepresentation.genProjectName(name), 
				vars);
		// @formatter:on

		String backendName = String.format("%s-backend", vars.get("projectName"));

		this.workspace.setWorkingProject(bootProject);
		this.shell.runCmd(bootProject.getPath(), String.format("chmod +x %s/scripts/keycloak/register-client.sh", backendName));
		this.shell.runCmd(bootProject.getPath(), "git init > /dev/null 2>&1 ");
		this.shell.runCmd(bootProject.getPath(), "git add . > /dev/null 2>&1");
		this.shell.runCmd(bootProject.getPath(), "git commit -m \"Inicial commit\" > /dev/null 2>&1 ");
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

}
