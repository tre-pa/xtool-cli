package br.xtool.core;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.ParsedLine;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

import br.xtool.core.command.CoreCommands;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

@Component
public class ConsoleProcessor {

	public void init() {
		AnsiConsole.systemInstall();
		try {
			// set up the completion
			CoreCommands commands = new CoreCommands();
			CommandLine cmd = new CommandLine(commands);
			Terminal terminal = TerminalBuilder.builder().build();
			// @formatter:off
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                    .parser(new DefaultParser())
                    .build();
            // @formatter:on
			commands.setReader(reader);
			String prompt = Ansi.ansi().bold().fg(Color.YELLOW).a("xtool:~ ").reset().toString();
			String rightPrompt = null;

			// start the shell and process input until the user quits with Ctl-D
			String line;
			while (true) {
				try {
					line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
					ParsedLine pl = reader.getParser().parse(line, 0);
					String[] arguments = pl.words().toArray(new String[0]);
					new CommandLine(commands).execute(arguments);
				} catch (UserInterruptException e) {
					System.out.println("Pressione Ctrl+D para sair");
				} catch (EndOfFileException e) {
					return;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			AnsiConsole.systemUninstall();
		}
	}
}
