package br.xtool;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String XTOOL_COMMAND_GROUP = "Xtool Core Commands";
	
	public final static String SPRINGBOOT_COMMAND_GROUP = "Xtool Spring Boot Commands";
	
	public final static String ANGULAR_COMMAND_GROUP = "Xtool Angular Commands";

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		new SpringApplicationBuilder(XtoolCliApplication.class)
				.headless(false)
				.run(args);


	}

}
