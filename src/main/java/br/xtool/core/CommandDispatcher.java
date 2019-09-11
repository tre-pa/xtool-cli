package br.xtool.core;

import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

import com.google.common.collect.Lists;

import br.xtool.core.command.CoreCommand;
import br.xtool.core.command.ExecCommand;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.ParseResult;
import picocli.shell.jline3.PicocliJLineCompleter;

/**
 * Classe responsável por interpretar a linha de comando e despachar a ação para os comandos.
 * 
 * @author jcruz
 *
 */
@Component
public class CommandDispatcher {

	/**
	 * Inicializa o processador de comandos.
	 */
	public void init() {
		AnsiConsole.systemInstall();
		try {
			// set up the completion
			CoreCommand coreCommand = new CoreCommand();
			CommandLine cmd = new CommandLine(coreCommand);
			addExec(cmd);
			Terminal terminal = TerminalBuilder.builder().build();
			// @formatter:off
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                    .parser(new DefaultParser())
                    .build();
            // @formatter:on
//			coreCommand.setReader(reader);
			String prompt = Ansi.ansi().bold().fg(Color.YELLOW).a("xtool:~ ").reset().toString();
			String rightPrompt = null;
			String line;
			while (true) {
				try {
					line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
					ParsedLine pl = reader.getParser().parse(line, 0);
					String[] arguments = pl.words().toArray(new String[0]);
					if(StringUtils.isBlank(arguments[0])) continue;
					ParseResult parseResult = cmd.parseArgs(arguments);
					// @formatter:off
					System.out.println(parseResult.asCommandLineList()
							.stream()
							.map(_cmd -> _cmd.getCommandName())
							.filter(StringUtils::isNotBlank)
							.collect(Collectors.toList()));
//					System.out.println(parseResult.subcommand().subcommand().matchedArgs());
					parseResult.subcommand().subcommand().matchedOptions()
							.stream()
							.forEach(op -> System.out.println(op.longestName()+" : "+op.getValue()));
					// @formatter:on
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

	private void addExec(CommandLine cmd) {
		CommandSpec execSpec = CommandSpec.forAnnotatedObject(new ExecCommand());
		// @formatter:off
		CommandSpec componentSpec = CommandSpec.create()
				.name("angular")
				.addOption(OptionSpec.builder("--name")
						.description("Nome do projeto")
						.completionCandidates(Lists.newArrayList("Angular", "SpringBoot", "SpringBoot:Fullstack"))
						.type(String.class)
						.required(false)
						.build())
				.addOption(OptionSpec.builder("--no-edit")
						.description("Sem edit")
						.arity("0")
						.required(false)
						.build());
		// @formatter:on
		execSpec.addSubcommand("angular", componentSpec);
		cmd.addSubcommand("exec", execSpec);
	}

}
