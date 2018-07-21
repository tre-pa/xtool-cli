package br.xtool.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.service.WorkspaceService;

@Component
public class ENgPageConverter implements Converter<String, ENgPage> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public ENgPage convert(String source) {
		if (this.workspaceService.getWorkingProject() instanceof ENgProject) {
			ENgProject project = ENgProject.class.cast(this.workspaceService.getWorkingProject());
			// @formatter:off
			return project.getNgPages()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Page."));
			// @formatter:on
		}
		return null;
	}

}
