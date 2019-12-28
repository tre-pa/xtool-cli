package br.xtool.implementation.representation.repo;

import br.xtool.implementation.representation.repo.directive.ComponentDescriptorRepresentationImpl;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.ModuleRepresentation;
import br.xtool.representation.repo.directive.ComponentDescriptorRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Objects;

@ToString
public class ComponentRepresentationImpl implements ComponentRepresentation {

    private Path path;

    private ModuleRepresentation module;

    public ComponentRepresentationImpl(Path path, ModuleRepresentation module) {
        this.path = path;
        this.module = module;
    }

    @Override
    public String getSimpleName() {
        return path.getFileName().toString();
    }

    @Override
    public String getName() {
        return module.getName().concat(":").concat(this.getSimpleName());
    }

    @Override
    public Path getTplPath() {
        return path.resolve("tpl");
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public ModuleRepresentation getModule() {
        return this.module;
    }

    @Override
    @SneakyThrows
    public ComponentDescriptorRepresentation getComponentDescriptor() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ComponentDescriptorRepresentation componentDescriptorRepresentation = mapper.readValue(this.path.resolve("xtool.yml").toFile(), ComponentDescriptorRepresentationImpl.class);
        return componentDescriptorRepresentation;
    }

//    @Override
//    public DescriptorYmlRepresentation getDescriptor() {
//        if(Objects.isNull(descriptor)) {
//            this.descriptor = DescriptorRepresentationImpl.Companion.of(this);
//        }
//        return descriptor;
//    }


}
