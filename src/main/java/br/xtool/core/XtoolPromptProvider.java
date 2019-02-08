package br.xtool.core;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.ProjectRepresentation.Type;

@Component
public class XtoolPromptProvider implements PromptProvider {

	@Autowired
	private Workspace workspace;

	@Override
	public AttributedString getPrompt() {
		String promptLabel = !this.workspace.getWorkingProject().getProjectType().equals(Type.NONE) ? String.format("xtool/%s ~ ", this.workspace.getWorkingProject().getName())
				: "xtool ~ ";
		return new AttributedString(promptLabel, AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW));
	}

}
