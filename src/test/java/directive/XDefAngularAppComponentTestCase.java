package directive;

import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@SpringBootTest
public class XDefAngularAppComponentTestCase {

    private Path repositoryPath = Paths.get("src/test/resources/xtool");

    private RepositoryRepresentation repositoryRepresentation = new RepositoryRepresentationImpl(repositoryPath);

    private Optional<ComponentRepresentation> angularAppComponent;

    @Before
    public void init() {
        this.angularAppComponent = repositoryRepresentation.getModules()
                .stream()
                .flatMap(module -> module.getComponents().stream())
                .peek(component -> System.out.println("Component: "+component.getName()))
                .filter(component -> component.getName().equals("angular:app"))
                .findFirst();
    }

    @Test
    public void angularComponentIsPresentTest() {
        Assert.assertTrue(angularAppComponent.isPresent());
    }

    @Test
    public void angularAppComponent_DefDirectiveIsPresentTest() {
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getDescription().equals("Gerador de scaffolding de projetos angular com DevExtreme."));
    }

    @Test
    public void angularAppComponent_DefVersionDirectiveIsPresentTest() {
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getVersion().equals("1.0.0"));
    }

    @Test
    public void angularAppComponent_DependesDirectiveNotPresentTest() {
        Assert.assertFalse(angularAppComponent.get().getDescriptor().getXDef().getDepends().isPresent());

    }

    @Test
    public void angularAppComponente_ParamsDirectiveIsPresent() {
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getParams().size() == 2);
        angularAppComponent.get().getDescriptor().getXDef().getParams()
                .stream()
                .forEach(p -> System.out.println("Id: "+p.getId()+", label: "+p.getLabel()+" type: "+p.getType()));
    }

    @Test
    public void angularAppComponent_TaskDirectiveIsPresent() {
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getTasks().size() == 6);
        angularAppComponent.get().getDescriptor().getXDef().getTasks()
                .stream()
                .forEach(p -> System.out.println(p.getTask()));
    }

}
