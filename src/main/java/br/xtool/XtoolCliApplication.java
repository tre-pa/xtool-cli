package br.xtool;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.jline.PromptProvider;

import br.xtool.core.WorkContext;

@SpringBootApplication
public class XtoolCliApplication implements PromptProvider {

	@Autowired
	private WorkContext workContext;

	public final static String CORE_COMMAND_GROUP = "Core Commands";
	public final static String PROJECT_COMMAND_GROUP = "Project Commands";
	public final static String SPRINGBOOT_COMMAND_GROUP = "Spring Boot Commands";

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}

	@Override
	public AttributedString getPrompt() {
		return new AttributedString(String.format("xtool@%s > ", workContext.getDirectory().getBaseName()), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}

}
