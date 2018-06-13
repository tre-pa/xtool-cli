package br.xtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String CORE_COMMAND_GROUP = "Core Commands Group";

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}
}
