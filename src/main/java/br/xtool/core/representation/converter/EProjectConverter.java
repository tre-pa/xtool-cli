package br.xtool.core.representation.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EProject;
import br.xtool.core.service.WorkspaceService;

@Component
public class EProjectConverter implements Converter<String, EProject> {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public EProject convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return this.workspaceService.getWorkspace().getProjects()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer projeto"));
			// @formatter:on
		}
		return null;
	}

}
