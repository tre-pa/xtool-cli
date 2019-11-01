package br.xtool.implementation.representation.repo.directive;

import br.xtool.completer.JpaEntityCompleter;
import br.xtool.converter.JpaEntityConverter;
import br.xtool.helper.BeanHelper;
import br.xtool.representation.repo.directive.ComponentDirectiveRepresentation;
import br.xtool.representation.repo.directive.ParamDirectiveRepresentation;
import br.xtool.type.JpaEntity;
import br.xtool.type.NgModule;
import org.springframework.util.Assert;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParamDirectiveRepresentationImpl implements ParamDirectiveRepresentation {

    private ComponentDirectiveRepresentation componentDirective;

    private Map<String, Object> params = new HashMap<>();

    /*
     * Mapeamento entre o tipo String e a classe de tipo correspondente.
     */
    private final Map<String, Class<?>> paramTypes = new HashMap() {{
        put("String", String.class);
        put("Boolean", Boolean.class);
        put("Integer", Integer.class);
        put("JpaEntity", JpaEntity.class);
        put("NgModule", NgModule.class);
    }};

    /*
     * Mapeamento entre o tipo String e a classe de converter correspondente.
     */
    private final Map<String, ? extends CommandLine.ITypeConverter<?>> paramConverters = new HashMap() {{
        put("JpaEntity", BeanHelper.getBean(JpaEntityConverter.class));
    }};

    /*
     * Mapeamento entre o tipo String e a classe de completer correspondente.
     */
    private final Map<String, ? extends Iterable<String>> paramCompleters = new HashMap() {{
        put("JpaEntity", BeanHelper.getBean(JpaEntityCompleter.class));
    }};

    private ParamDirectiveRepresentationImpl(ComponentDirectiveRepresentation component, Map<String, Object> params) {
        this.componentDirective = component;
        this.params = params;
    }

    @Override
    public String getId() {
        return (String) params.get("id");
    }

    @Override
    public String getLabel() {
        return (String) params.get("label");
    }

    @Override
    public String getDescription() {
        return (String) params.get("description");
    }

    @Override
    public boolean isRequired() {
        return (boolean) params.getOrDefault("required", false);
    }

    @Override
    public CommandLine.Model.OptionSpec getOptionSpec() {
        CommandLine.Model.OptionSpec.Builder opSpecBuilder = CommandLine.Model.OptionSpec.builder(this.getLabel());
        opSpecBuilder.type(this.getParamType());
        getConverter().ifPresent(opSpecBuilder::converters);
        getCompleter().ifPresent(opSpecBuilder::completionCandidates);
        return opSpecBuilder.build();
    }

    @Override
    public Class<?> getParamType() {
        return paramTypes.get(this.params.getOrDefault("type", "String"));
    }

    @Override
    public Optional<? extends CommandLine.ITypeConverter<?>> getConverter() {
        if(paramConverters.containsKey(this.getParamType().getSimpleName())) {
            return Optional.of(this.paramConverters.get(this.getParamType().getSimpleName()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<? extends Iterable<String>> getCompleter() {
        if(paramCompleters.containsKey(this.getParamType().getSimpleName())) {
            return Optional.of(paramCompleters.get(this.getParamType().getSimpleName()));
        }
        return Optional.empty();
    }

    /**
     * Factory
     * @param component
     * @param paramMap
     * @return
     */
    public static ParamDirectiveRepresentationImpl of(ComponentDirectiveRepresentation component, Map<String, Object> paramMap) {
        Assert.isTrue(paramMap.containsKey("id"), "O atributo 'id' do parâmetro é obrigatório. (Component: " + component.getDescriptor().getComponent().getName() + ")");
        Assert.isTrue(paramMap.containsKey("label"), "O atributo 'label' do parâmetro é obrigatório. (Component: " + component.getDescriptor().getComponent().getName()  + ")");
        Assert.isTrue(paramMap.containsKey("description"), "O atributo 'description' do parâmetro é obrigatório. (Component: " + component.getDescriptor().getComponent().getName()  + ")");
        return new ParamDirectiveRepresentationImpl(component, paramMap);
    }


}
