package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootRepository;
import br.xtool.service.WorkspaceService;
import br.xtool.core.representation.EBootProject;

@Component
public class EJavaRepositoryShellConverter implements Converter<String, EBootRepository> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EBootRepository convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspaceService.getWorkingProject() instanceof EBootProject) {
				EBootProject project = EBootProject.class.cast(this.workspaceService.getWorkingProject());
				// @formatter:off
				return project.getRepositories()
					.stream()
					.filter(e -> e.getName().equals(source))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Erro ao converter repositório."));
				// @formatter:on
			}
		}
		return null;
	}

}
