package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.representation.EBootProject;

@Component
public class EJpaEntityShellConverter implements Converter<String, EJpaEntity> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EJpaEntity convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspaceService.getWorkingProject() instanceof EBootProject) {
				EBootProject project = EBootProject.class.cast(this.workspaceService.getWorkingProject());
				// @formatter:off
				return project.getEntities()
					.stream()
					.filter(e -> e.getName().equals(source))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Erro ao converter entidade."));
				// @formatter:on
			}
		}
		return null;
	}

}
