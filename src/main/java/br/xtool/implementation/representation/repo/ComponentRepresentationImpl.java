package br.xtool.implementation.representation.repo;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.directive.XDescriptorRepresentation;
import br.xtool.representation.repo.ModuleRepresentation;
import lombok.ToString;

import java.nio.file.Path;

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
    public XDescriptorRepresentation getDescriptor() {
        throw new UnsupportedOperationException();
    }


}
