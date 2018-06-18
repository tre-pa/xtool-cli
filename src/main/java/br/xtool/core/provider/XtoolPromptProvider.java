package br.xtool.core.provider;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.PathService;

@Component
public class XtoolPromptProvider implements PromptProvider {

	@Autowired
	private PathService pathService;

	@Override
	public AttributedString getPrompt() {
		if (pathService.hasWorkingDirectory()) {
			return new AttributedString(String.format("xtool@%s > ", pathService.getWorkingDirectoryBaseName()), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
		}
		return new AttributedString("xtool > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}

}
