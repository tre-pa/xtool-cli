package br.xtool.core.provider;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.service.WorkspaceService;

@Component
public class XtoolPromptProvider implements PromptProvider {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public AttributedString getPrompt() {
		String promptLabel = !this.workspaceService.getWorkingProject().getProjectType().equals(ProjectType.NONE) ? String.format("xtool/%s ~ ", this.workspaceService.getWorkingProject().getName())
				: "xtool ~ ";
		return new AttributedString(promptLabel, AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW));
	}

}
