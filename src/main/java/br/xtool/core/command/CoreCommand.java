package br.xtool.core.command;

import picocli.CommandLine.Command;

@Command(name = "", description = "Example interactive shell with completion", footer = { "", "Press Ctl-D to exit." },
		subcommands = {ClearCommand.class})
public class CoreCommand implements Runnable {
	
//	@Getter
//	private LineReaderImpl reader;
//	private PrintWriter out;


//	public void setReader(LineReader reader) {
//		this.reader = (LineReaderImpl) reader;
//		out = reader.getTerminal().writer();
//	}

	public void run() {
//		out.println(new CommandLine(this).getUsageMessage());
	}
}