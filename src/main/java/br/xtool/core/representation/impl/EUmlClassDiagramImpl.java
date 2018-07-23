package br.xtool.core.representation.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.representation.EUmlEnum;
import br.xtool.core.representation.EUmlRelationship;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.LeafType;

public class EUmlClassDiagramImpl implements EUmlClassDiagram {

	private ClassDiagram classDiagram;

	private EUmlClassDiagramImpl(ClassDiagram classDiagram) {
		super();
		this.classDiagram = classDiagram;
	}

	@Override
	public Set<EUmlClass> getClasses() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.CLASS))
			 .map(EUmlClassImpl::new)
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	@Override
	public Set<EUmlEnum> getEnums() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.ENUM))
			 .map(EUmlEnumImpl::new)
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	@Override
	public Set<EUmlRelationship> getRelationships() {
		// @formatter:off
		return this.classDiagram.getEntityFactory().getLinks().stream()
				.map(link -> new EUmlRelationshipImpl(getClasses(), link))
				.collect(Collectors.toSet());
		// @formatter:on
	}

	public static EUmlClassDiagram of(Path path) throws IOException {
		//		String diagram = FileUtils.readFileToString(new File(path), "UTF-8");
		String diagram = new String(Files.readAllBytes(path));
		SourceStringReader reader = new SourceStringReader(diagram.replace("```plantuml", "@startuml").replace("```", "@enduml"));
		// @formatter:off
		ClassDiagram classDiagram = reader.getBlocks().stream()
				.map(BlockUml::getDiagram)
				.filter(ClassDiagram.class::isInstance)
				.map(ClassDiagram.class::cast)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Diagrama de classe não encontrado ou com erros."));
		// @formatter:on
		return new EUmlClassDiagramImpl(classDiagram);
	}

}
