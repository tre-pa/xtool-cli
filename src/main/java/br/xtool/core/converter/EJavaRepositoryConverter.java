package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaRepository;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.WorkspaceService;

@Component
public class EJavaRepositoryConverter implements Converter<String, EJavaRepository> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EJavaRepository convert(String source) {
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
