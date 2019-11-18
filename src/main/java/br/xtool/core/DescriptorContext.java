package br.xtool.core;

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
     * Retorna a referência dos parametros.
     *
     * @return
     */
    private Map<String, Object> params;

    /**
     * Processa a expressão SpEL.
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

    public DescriptorContext(Map<String, Object> params) {
        this.params = params;
    }
}
