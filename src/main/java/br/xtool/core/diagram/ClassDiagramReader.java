package br.xtool.core.diagram;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EPackage;
import br.xtool.core.representation.ESpringBootProject;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

@Component
public class ClassDiagramReader {

	private Map<String, JavaClassSource> javaClassSources = new HashMap<>();

	public void parse(String diagram) {
		SourceStringReader reader = new SourceStringReader(this.nomalizeDiagram(diagram));
		for (BlockUml blockUml : reader.getBlocks()) {
			if (blockUml.getDiagram() instanceof ClassDiagram) {
				ClassDiagram classDiagram = (ClassDiagram) blockUml.getDiagram();
				this.parseClasses(classDiagram);
			}
		}

	}

	private String nomalizeDiagram(String diagram) {
		diagram = diagram.replace("```plantuml", "@startuml");
		return diagram.replace("```", "@enduml");
	}

	/*
	 * 
	 */
	private void parseClasses(ClassDiagram classDiagram) {
		Collection<IGroup> groups = classDiagram.getGroups(false);
		for (IGroup group : groups) {
			String packageName = group.getCode().getFullName();
			for (ILeaf leaf : group.getLeafsDirect()) {
				JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
				javaClass.setPackage(packageName);
				javaClass.setName(leaf.getDisplay().asStringWithHiddenNewLine());
				this.javaClassSources.put(javaClass.getName(), javaClass);
			}
		}
	}

	@SneakyThrows
	public void write(ESpringBootProject project) {
		for (JavaClassSource javaClass : this.javaClassSources.values()) {
			EPackage ePackage = EPackage.of(javaClass.getPackage());
			String javaFile = FilenameUtils.concat(project.getMainDir(), String.format("%s/%s.java", ePackage.getDir(), javaClass.getName()));
			FileUtils.writeStringToFile(new File(javaFile), javaClass.toString(), "UTF-8");
		}
	}

}
