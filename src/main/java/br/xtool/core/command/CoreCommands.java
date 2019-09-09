package br.xtool.core.command;

import java.io.PrintWriter;

import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;

import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "", description = "Example interactive shell with completion", footer = { "", "Press Ctl-D to exit." }, subcommands = { ClearCommand.class })
public class CoreCommands implements Runnable {
	@Getter
	private LineReaderImpl reader;
	private PrintWriter out;

	public CoreCommands() {}

	public void setReader(LineReader reader) {
		this.reader = (LineReaderImpl) reader;
		out = reader.getTerminal().writer();
	}

	public void run() {
		out.println(new CommandLine(this).getUsageMessage());
	}
}