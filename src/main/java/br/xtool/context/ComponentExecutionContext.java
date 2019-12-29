package br.xtool.context;

import br.xtool.core.TemplateParserContext;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.directive.DescriptorParamRepresentation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import picocli.CommandLine;

import java.util.Collection;
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
    private String destination;

    /**
     * Projeto de trabalho do workspace.
     */
    private ProjectRepresentation project;

    /**
     * Mapa com os valores do paramentros./
     */
    private Map<String, Object> params = new HashMap<>();

    private ComponentExecutionContext() {}

    public static ComponentExecutionContext of(ComponentRepresentation componentRepresentation, ProjectRepresentation project, CommandLine.ParseResult parseResult) {
        ComponentExecutionContext executionContext = new ComponentExecutionContext();
        executionContext.params = parseResult.subcommand().subcommand().matchedOptions().stream()
                .collect( Collectors.toMap(
                        op->  findParamIdByLabel(componentRepresentation, op.names()[0]),
                        op -> parseResult.subcommand().subcommand().matchedOptionValue(op.names()[0], op.defaultValue())));
        executionContext.project = project;
        return executionContext;
    }

    private static String findParamIdByLabel(ComponentRepresentation componentRepresentation, String paramLabel) {
        return componentRepresentation.getComponentDescriptor().getParams()
            .stream()
            .filter(param -> param.getLabel().equals(paramLabel))
            .map(DescriptorParamRepresentation::getId)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Parametro com label '%s' não encontrado no componente '%s'", paramLabel, componentRepresentation.getComponentDescriptor().getName())));
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

    public Boolean parseAsBoolean(String exp) {
        return Boolean.valueOf(this.parse(exp, String.class));
    }

    public <T> T parse(String exp, Class<T> clazz) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(exp, new TemplateParserContext()).getValue(this, clazz);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ProjectRepresentation getProject() {
        return project;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
