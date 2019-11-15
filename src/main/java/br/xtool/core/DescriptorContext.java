package br.xtool.core;

import br.xtool.representation.ProjectRepresentation;
import lombok.Getter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Map;

/**
 * Classe com o payload para a execução das terefas.
 */
@Getter
public class DescriptorContext {
    /**
     * Retorna a referência do projeto.
     *
     * @return
     */
    private ProjectRepresentation project;

    /**
     * Retorna a referência dos parametros.
     *
     * @return
     */
    private Map<String, Object> params;

    /**
     * Processa a expressão SpEL.
     * @param exp
     * @return
     */
    public String parse(String exp) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(exp, new TemplateParserContext()).getValue(this,String.class);
    }

    public DescriptorContext(ProjectRepresentation project, Map<String, Object> params) {
        this.project = project;
        this.params = params;
    }
}
