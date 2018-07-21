package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.service.WorkspaceService;

@Component
public class EJavaEntityConverter implements Converter<String, EJavaEntity> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EJavaEntity convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspaceService.getWorkingProject() instanceof ESBootProject) {
				ESBootProject project = ESBootProject.class.cast(this.workspaceService.getWorkingProject());
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
