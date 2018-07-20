package br.xtool.core.provider;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class XtoolPromptProvider implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("xtool ~ ", AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW));
	}

}
