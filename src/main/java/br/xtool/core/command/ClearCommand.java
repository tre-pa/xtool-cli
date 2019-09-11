package br.xtool.core.command;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "clear", description = "Limpa a tela")
public class ClearCommand implements Callable<Void> {

	@ParentCommand
	private CoreCommand parent;

	@Override
	public Void call() throws Exception {
		System.out.println("Clear command!");
//		parent.getReader().clearScreen();
		return null;
	}

}
