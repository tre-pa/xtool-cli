package br.xtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XtoolCliApplication {

	public final static String XTOOL_COMMAND_GROUP = "Xtool Commands";

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}

}
