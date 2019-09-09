package br.xtool.core.command;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "clear", mixinStandardHelpOptions = false, description = "Clears the screen")
public class ClearCommand implements Callable<Void> {

	@ParentCommand
	private CoreCommands parent;

	@Override
	public Void call() throws Exception {
		parent.getReader().clearScreen();
		return null;
	}

}
