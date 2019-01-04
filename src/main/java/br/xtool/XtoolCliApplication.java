package br.xtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String XTOOL_COMMAND_GROUP = "Xtool Commands";
	
	public final static String SPRINGBOOT_COMMAND_GROUP = "Spring Boot Commands";

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(XtoolCliApplication.class, args);
	}

}
