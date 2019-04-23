package br.xtool.plantuml;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import br.xtool.core.implementation.representation.PlantClassDiagramRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false" })
public class GenPlantDiagramTestCase {

	@Autowired
	private Shell shell;

	private PlantClassDiagramRepresentation diagram = PlantClassDiagramRepresentationImpl.of(Paths.get("src/test/java/br/xtool/plantuml/main.plantuml"));

	@Test
	public void count15ClassTest() throws Exception {
//		PlantClassDiagramRepresentation diagram = PlantClassDiagramRepresentationImpl.of(Paths.get("src/test/java/br/xtool/plantuml/main.plantuml"));
		assertTrue(diagram.getClasses().size() == 15);
	}

	@Test
	public void findClassConexaoByNameTest() {
		Optional<PlantClassRepresentation> clazz = diagram.findClassByName("Conexao");
		assertTrue(clazz.isPresent());
	}

	@Test
	public void countRelationships() {
		Optional<PlantClassRepresentation> clazz = diagram.findClassByName("ConfiguracaoKit");
		if (clazz.isPresent()) {
			System.out.println(String.format("Relacionamento de classe: %s", clazz.get().getName()));
			// @formatter:off
			clazz.get().getRelationships()
				.stream()
				.forEach(r -> System.out.println(String.format("Source: %s, Target: %s", r.getSourceClass().getName(), r.getTargetClass().getName())));
			// @formatter:on
			assertTrue(clazz.get().getRelationships().size() == 0);
		}
	}
}
