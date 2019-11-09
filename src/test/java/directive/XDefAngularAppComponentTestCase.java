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

    @Before
    public void init() {

    }

    /**
     * Teste que lê a descrição e a versão do componente angular:app
     */
    @Test
    public void readAngularAppXtoolDescriptor_DefDirective() {
        Optional<ComponentRepresentation> angularAppComponent = repositoryRepresentation.getModules()
                .stream()
                .flatMap(module -> module.getComponents().stream())
                .peek(component -> System.out.println("Component: "+component.getName()))
                .filter(component -> component.getName().equals("angular:app"))
                .findFirst();

        Assert.assertTrue(angularAppComponent.isPresent());
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getDescription().equals("Gerador de scaffolding de projetos angular com DevExtreme."));
        Assert.assertTrue(angularAppComponent.get().getDescriptor().getXDef().getVersion().equals("1.0.0"));
        Assert.assertFalse(angularAppComponent.get().getDescriptor().getXDef().getDepends().isPresent());

    }

}
