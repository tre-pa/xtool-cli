package br.xtool.core.provider;

import java.util.Objects;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.event.ChangeWorkingProjectEvent;
import br.xtool.core.representation.EProject;

@Component
public class XtoolPromptProvider implements PromptProvider {

	private EProject workingProject;

	@Override
	public AttributedString getPrompt() {
		String promptLabel = Objects.nonNull(this.workingProject) ? String.format("xtool/%s ~ ", this.workingProject.getName()) : "xtool ~ ";
		return new AttributedString(promptLabel, AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW));
	}

	@EventListener
	private void onChangeWorkingProject(ChangeWorkingProjectEvent evt) {
		this.workingProject = evt.getProject();
	}
}
