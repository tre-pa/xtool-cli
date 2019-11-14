package directive;

import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@SpringBootTest
public class XDefAngularServiceComponentTestCase {

    private Path repositoryPath = Paths.get("src/test/resources/xtool");

    private RepositoryRepresentation repositoryRepresentation = new RepositoryRepresentationImpl(repositoryPath);

    /**
     * Teste que lê a descrição e a versão do componente angular:app
     */
    @Test
    public void readAngularAppXtoolDescriptor_DefDirective() {
        Optional<ComponentRepresentation> angularServiceComponent = repositoryRepresentation.getModules()
                .stream()
                .flatMap(module -> module.getComponents().stream())
                .peek(component -> System.out.println("Component: "+component.getName()))
                .filter(component -> component.getName().equals("angular:service"))
                .findFirst();

        Assert.assertTrue(angularServiceComponent.isPresent());
        Assert.assertTrue(angularServiceComponent.get().getDescriptor().getDef().getDescription().equals("Gerador de service angular"));
        Assert.assertTrue(angularServiceComponent.get().getDescriptor().getDef().getDepends().isPresent());
        System.out.println(angularServiceComponent.get().getDescriptor().getDef().getDepends().get());
    }

}
