package br.xtool;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class XtoolCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(XtoolCliApplication.class, args);
	}
	
	@Bean
	public PromptProvider promptProvider() {
	    return () -> new AttributedString("xtool > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}


