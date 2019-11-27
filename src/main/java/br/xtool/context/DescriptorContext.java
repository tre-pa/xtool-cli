package br.xtool.context;

import br.xtool.core.TemplateParserContext;
import lombok.Getter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.nio.file.Path;
import java.util.Map;

/**
 * Classe com o payload para a execução das terefas.
 */
@Getter
@Deprecated
public class DescriptorContext {

    private Path destination;

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

    public void updateDestination(Path destination) {
        this.destination = destination;
    }

    public <T> T parse(String exp, Class<T> clazz) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(exp, new TemplateParserContext()).getValue(this, clazz);
    }

    public DescriptorContext(Path defaultDestionation ,Map<String, Object> params) {
        this.destination = defaultDestionation;
        this.params = params;
    }
}
