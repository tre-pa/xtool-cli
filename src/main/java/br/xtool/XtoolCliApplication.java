package br.xtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String CORE_COMMAND_GROUP = "Core Commands";
	public final static String PROJECT_COMMAND_GROUP = "Project Commands";
	public final static String SPRINGBOOT_COMMAND_GROUP = "Spring Boot Commands";
	public final static String ANGULAR_COMMAND_GROUP = "Angular Commands";
	public final static String GENERATORS_COMMAND_GROUP = "Generators Commands";
	public final static String INFO_COMMAND_GROUP = "Info Commands";

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}

}
