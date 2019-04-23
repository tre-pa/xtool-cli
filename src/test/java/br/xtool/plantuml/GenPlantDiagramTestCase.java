package br.xtool.plantuml;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

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

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false" })
public class GenPlantDiagramTestCase {

	@Autowired
	private Shell shell;

	@Test
	public void count15ClassTest() throws Exception {
		PlantClassDiagramRepresentation diagram = PlantClassDiagramRepresentationImpl.of(Paths.get("src/test/java/br/xtool/plantuml/main.plantuml"));
		assertTrue(diagram.getClasses().size() == 15);
	}

}
