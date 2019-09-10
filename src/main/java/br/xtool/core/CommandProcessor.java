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

import br.xtool.core.command.CoreCommand;
import br.xtool.core.command.ExecCommand;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

@Component
public class CommandProcessor {
	
	/**
	 * Inicializa o processador de comandos.
	 */
	public void init() {
		AnsiConsole.systemInstall();
		try {
			// set up the completion
			CoreCommand coreCommand = new CoreCommand();
			CommandLine cmd = new CommandLine(coreCommand);
			cmd.addSubcommand("exec", new ExecCommand());
			Terminal terminal = TerminalBuilder.builder().build();
			// @formatter:off
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                    .parser(new DefaultParser())
                    .build();
            // @formatter:on
			coreCommand.setReader(reader);
			String prompt = Ansi.ansi().bold().fg(Color.YELLOW).a("xtool:~ ").reset().toString();
			String rightPrompt = null;
			String line;
			while (true) {
				try {
					line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
					ParsedLine pl = reader.getParser().parse(line, 0);
					String[] arguments = pl.words().toArray(new String[0]);
					cmd.execute(arguments);
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
