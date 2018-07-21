package br.xtool.core.provider;

import java.util.Objects;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.service.WorkspaceService;

@Component
public class XtoolPromptProvider implements PromptProvider {

	//	private EProject workingProject;

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public AttributedString getPrompt() {
		String promptLabel = Objects.nonNull(this.workspaceService.getWorkingProject()) ? String.format("xtool/%s ~ ", this.workspaceService.getWorkingProject().getName()) : "xtool ~ ";
		return new AttributedString(promptLabel, AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW));
	}

	//	@EventListener
	//	private void onChangeWorkingProject(ChangeWorkingProjectEvent evt) {
	//		this.workingProject = evt.getProject();
	//	}
}
