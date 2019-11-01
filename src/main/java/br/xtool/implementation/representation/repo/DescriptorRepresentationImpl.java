package br.xtool.implementation.representation.repo;

import br.xtool.implementation.representation.repo.directive.ComponentDirectiveRepresentationImpl;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.DescriptorRepresentation;
import br.xtool.representation.repo.directive.ComponentDirectiveRepresentation;
import lombok.SneakyThrows;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

public class DescriptorRepresentationImpl implements DescriptorRepresentation {

    private ComponentRepresentation component;

    private Map<String, Object> descriptor;

    private DescriptorRepresentationImpl(ComponentRepresentation component) {
        this.component = component;
    }

    @Override
    public ComponentDirectiveRepresentation getComponentDirective() {
        Assert.isTrue(descriptor.containsKey("component"), String.format("A diretiva 'component' é obrigatória em %s", component.getPath().toAbsolutePath()));
        Map<String, Object> componentDirectiveMap = (Map<String, Object>) descriptor.get("component");
        return ComponentDirectiveRepresentationImpl.of(this, componentDirectiveMap);
    }

    @Override
    public ComponentRepresentation getComponent() {
        return this.component;
    }

    @SneakyThrows
    public static DescriptorRepresentation of(ComponentRepresentation component) {
        Assert.isTrue(Files.exists(component.getPath().resolve(DescriptorRepresentation.DESCRIPTOR_FILENAME)), "Não foi possível encontrar o arquivo descritor no componente: "+component.getPath());
        DescriptorRepresentationImpl descriptorRepresentation = new DescriptorRepresentationImpl(component);
        InputStream input = Files.newInputStream(component.getPath().resolve(DescriptorRepresentation.DESCRIPTOR_FILENAME));
        descriptorRepresentation.descriptor = new Yaml().load(input);
        return descriptorRepresentation;
    }
}
