package br.xtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.shell.standard.FileValueProvider;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String CORE_COMMAND_GROUP = "Core Commands";
	public final static String PROJECT_COMMAND_GROUP = "Project Commands";
	public final static String SPRINGBOOT_COMMAND_GROUP = "Spring Boot Commands";


	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}
}
