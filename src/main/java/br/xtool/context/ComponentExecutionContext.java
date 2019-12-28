package br.xtool.context;

import br.xtool.core.TemplateParserContext;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.repo.ComponentRepresentation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que representa o contexto de execução do componente.
 */
@ToString
public class ComponentExecutionContext {

    /**
     * Caminho relativo de destino das tarefas;
     */
    @Getter
    @Setter
    private String destination;

    /**
     * Projeto de trabalho do workspace.
     */
    @Getter
    private ProjectRepresentation project;

    /**
     * Mapa com os valores do paramentros./
     */
    @Getter
    private Map<String, Object> params = new HashMap<>();

    private ComponentExecutionContext() {}

    public static ComponentExecutionContext of(ComponentRepresentation componentRepresentation, ProjectRepresentation project, CommandLine.ParseResult parseResult) {
        ComponentExecutionContext executionContext = new ComponentExecutionContext();
        executionContext.params = parseResult.subcommand().subcommand().matchedOptions().stream()
                .collect( Collectors.toMap(op->  op.names()[0].replace("--", ""), op -> parseResult.subcommand().subcommand().matchedOptionValue(op.names()[0], op.defaultValue())));
        executionContext.project = project;
        return executionContext;
    }

    /**
     * Realiza o parser SpEL de exp.
     *
     * @param exp
     * @return
     */
    public String parse(String exp) {
        return this.parse(exp, String.class);
    }

    public <T> T parse(String exp, Class<T> clazz) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(exp, new TemplateParserContext()).getValue(this, clazz);
    }

}
