package br.xtool.implementation;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.fusesource.jansi.Ansi;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Service;

import br.xtool.core.Console;

@Service
public class ConsoleImpl implements Console {
	
	private Terminal terminal;
	
	@PostConstruct
	private void init() throws IOException {
//		this.terminal = TerminalBuilder.builder().build();
	}

	@Override
	public void clearScreen() {

	}
	
	@Override
	public void println(String msg) {
		System.out.println(Ansi.ansi().render(msg).reset());
	}

}
