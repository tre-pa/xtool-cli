package br.xtool.implementation;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.xtool.core.Console;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;
import org.jline.reader.impl.LineReaderImpl;

@Service
public class ConsoleImpl implements Console {

    @Autowired
    private CommandLine cmd;

    @Autowired
	private ApplicationEventPublisher publisher;

    private Terminal terminal;

    private LineReader reader;

    @EventListener(ContextRefreshedEvent.class)
    private void init() throws IOException {
    	AnsiConsole.systemInstall();
        this.terminal = TerminalBuilder.builder().build();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                .parser(new DefaultParser())
                .build();
        String prompt = Ansi.ansi().bold().fg(Ansi.Color.YELLOW).a("xtool:~ ").reset().toString();
        String rightPrompt = null;
        String line;
        try {
            while (true) {
                try {
                    line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
                    ParsedLine pl = reader.getParser().parse(line, 0);
                    String[] arguments = pl.words().toArray(new String[0]);
//					if (StringUtils.isBlank(arguments[0])) continue;
                    CommandLine.ParseResult parseResult = cmd.parseArgs(arguments);
                    publisher.publishEvent(parseResult);
                    // @formatter:off
//                    System.out.println(parseResult.asCommandLineList()
//                            .stream()
//                            .map(_cmd -> _cmd.getCommandName())
//                            .filter(StringUtils::isNotBlank)
//                            .collect(Collectors.toList()));
////					System.out.println(parseResult.subcommand().subcommand().matchedArgs());
//                    parseResult.subcommand().subcommand().matchedOptions()
//                            .stream()
//                            .forEach(op -> System.out.println(op.longestName() + " : " + op.getValue()));
                    // @formatter:on
                } catch (UserInterruptException e) {
					this.println("Pressione Ctrl+D para sair.");
                } catch (CommandLine.UnmatchedArgumentException e) {
                    this.println("Argumento não encontrado. ".concat(e.getMessage()));
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

    @Override
    public void clearScreen() {
		((LineReaderImpl) reader).clearScreen();
    }

    @Override
    public void println(String msg) {
        System.out.println(Ansi.ansi().render(msg).reset());
    }

}
