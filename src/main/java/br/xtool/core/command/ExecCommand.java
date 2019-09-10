package br.xtool.core.command;

import picocli.CommandLine.Command;

@Command(name = "exec", mixinStandardHelpOptions = false, description = "Executa um componente")
public class ExecCommand implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Oi");
	}
	
}
