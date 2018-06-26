package br.xtool;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.FileValueProvider;

import br.xtool.core.PathService;

@SpringBootApplication
public class XtoolCliApplication implements PromptProvider {

	@Autowired
	private PathService pathService;

	public final static String CORE_COMMAND_GROUP = "Core Commands";
	public final static String PROJECT_COMMAND_GROUP = "Project Commands";
	public final static String SPRINGBOOT_COMMAND_GROUP = "Spring Boot Commands";

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}

	@Override
	public AttributedString getPrompt() {
		if (pathService.hasWorkingDirectory()) {
			return new AttributedString(String.format("xtool@%s > ", pathService.getWorkingDirectoryBaseName()), AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
		}
		return new AttributedString("xtool > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}
