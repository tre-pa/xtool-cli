package br.xtool.core.implementation;

import org.fusesource.jansi.Ansi;
import org.springframework.stereotype.Service;

import br.xtool.core.Console;

@Service
public class ConsoleImpl implements Console {

	@Override
	public void clearScreen() {

	}

	@Override
	public void println(String msg) {
		System.out.println(Ansi.ansi().render(msg).reset());
	}

}
