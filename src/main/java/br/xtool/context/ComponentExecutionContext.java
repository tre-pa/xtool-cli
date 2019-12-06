package br.xtool.context;

import br.xtool.core.TemplateParserContext;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.repo.ComponentRepresentation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa o contexto de execução do componente.
 */
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
    private Map<String, Object> params = new HashMap<>();

    private ComponentExecutionContext() {}

    public static ComponentExecutionContext of(ComponentRepresentation componentRepresentation, ProjectRepresentation project, CommandLine.ParseResult parseResult) {
        ComponentExecutionContext executionContext = new ComponentExecutionContext();
        executionContext.params = componentRepresentation.getDescriptor().getComponentDef().getParamDefValues(parseResult);
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
