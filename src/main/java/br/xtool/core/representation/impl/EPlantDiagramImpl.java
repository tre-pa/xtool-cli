package br.xtool.core.representation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantClassDiagram;
import br.xtool.core.representation.EPlantEnum;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.LeafType;

public class EPlantDiagramImpl implements EPlantClassDiagram {

	private ClassDiagram classDiagram;

	private EPlantDiagramImpl(ClassDiagram classDiagram) {
		super();
		this.classDiagram = classDiagram;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClassDiagram#getClasses()
	 */
	@Override
	public Set<EPlantClass> getClasses() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.CLASS))
			 .map(leaf -> new EPlantClassImpl(this,this.classDiagram, leaf))
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClassDiagram#getEnums()
	 */
	@Override
	public Set<EPlantEnum> getEnums() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.ENUM))
			 .map(leaf -> new EPlantEnumImpl(this, leaf))
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	@SneakyThrows
	public static EPlantClassDiagram of(Path path) {
		if (Files.notExists(path)) throw new IllegalArgumentException("Diagrama de classe não encontrado");
		//		String diagram = FileUtils.readFileToString(new File(path), "UTF-8");
		String diagram = new String(Files.readAllBytes(path));
		SourceStringReader reader = new SourceStringReader(diagram.replace("```plantuml", "@startuml").replace("```", "@enduml"));
		// @formatter:off
		ClassDiagram classDiagram = reader.getBlocks().stream()
				.map(BlockUml::getDiagram)
				.filter(ClassDiagram.class::isInstance)
				.map(ClassDiagram.class::cast)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Diagrama de classe com erros."));
		// @formatter:on
		return new EPlantDiagramImpl(classDiagram);
	}

}
