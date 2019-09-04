package br.xtool;

import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class XtoolCliApplication implements CommandLineRunner {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new SpringApplicationBuilder(XtoolCliApplication.class).headless(false).run(args);

	}

	@Override
	public void run(String... args) throws Exception {}

}
