package br.xtool;

import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.xtool.core.CommandProcessor;

@SpringBootApplication
public class XtoolCliApplication implements CommandLineRunner {
	
	@Autowired
	private CommandProcessor consoleProcessor;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new SpringApplicationBuilder(XtoolCliApplication.class).headless(false).run(args);

	}

	@Override
	public void run(String... args) throws Exception {
		consoleProcessor.init();
	}

}
