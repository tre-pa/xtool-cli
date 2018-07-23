package br.xtool.core.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.FileService;
import br.xtool.core.service.WorkspaceService;

@Component
public class EBootProjectV1Factory implements Function<String, EBootProject> {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EBootProject apply(String name) {
		Map<String, Object> vars = new HashMap<>();
		vars.put("templatePath", "springboot/1.5.x/archetype");
		vars.put("projectName", EBootProject.genProjectName(name));
		vars.put("rootPackage", EBootProject.genRootPackage(name));
		vars.put("baseClassName", EBootProject.genBaseClassName(name));

		this.fs.copy("${templatePath}/src/main/java/SpringBootApplication.java.vm", "${projectName}/src/main/java/${rootPackage.dir}/${baseClassName}Application.java", vars);
		this.fs.copy("${templatePath}/src/main/resources/application.properties.vm", "${projectName}/src/main/resources/application.properties", vars);
		this.fs.copy("${templatePath}/gitignore", "${projectName}/.gitignore", vars);
		this.fs.copy("${templatePath}/pom.xml.vm", "${projectName}/pom.xml", vars);

		return null;
	}

}
