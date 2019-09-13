package br.xtool.implementation.representation;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.representation.plantuml.PlantClassRepresentation;
import br.xtool.representation.plantuml.PlantEnumRepresentation;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.LeafType;

public class PlantClassDiagramRepresentationImpl implements PlantClassDiagramRepresentation {

	private Path path;

	private SourceStringReader sourceStringReader;

	private ClassDiagram classDiagram;

	private PlantClassDiagramRepresentationImpl(Path path, SourceStringReader sourceStringReader, ClassDiagram classDiagram) {
		super();
		this.path = path;
		this.sourceStringReader = sourceStringReader;
		this.classDiagram = classDiagram;
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClassDiagram#getClasses()
	 */
	@Override
	public Set<PlantClassRepresentation> getClasses() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.CLASS))
			 .map(leaf -> new PlantClassRepresentationImpl(this,this.classDiagram, leaf))
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClassDiagram#getEnums()
	 */
	@Override
	public Set<PlantEnumRepresentation> getEnums() {
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf -> leaf.getEntityType().equals(LeafType.ENUM))
			 .map(leaf -> new PlantEnumRepresentationImpl(this, leaf))
			 .collect(Collectors.toSet());
		// @formatter:on
	}

	@Override
	@SneakyThrows
	public byte[] getPng() {
		String diagram = new String(Files.readAllBytes(path));
		SourceStringReader reader = new SourceStringReader(diagram);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		reader.generateImage(os);
		return os.toByteArray();
	}

	@Override
	public Optional<PlantClassRepresentation> findClassByName(String name) {
		// @formatter:off
		return this.getClasses()
				.parallelStream()
				.filter(clazz -> clazz.getName().equals(name))
				.findAny();
		// @formatter:on
	}

	@SneakyThrows
	public static PlantClassDiagramRepresentation of(Path path) {
		if (Files.notExists(path)) throw new IllegalArgumentException("Diagrama de classe não encontrado");
		// String diagram = FileUtils.readFileToString(new File(path), "UTF-8");
		String diagram = new String(Files.readAllBytes(path));
		SourceStringReader reader = new SourceStringReader(diagram);
		// @formatter:off
		ClassDiagram classDiagram = reader.getBlocks().stream()
				.map(BlockUml::getDiagram)
				.filter(ClassDiagram.class::isInstance)
				.map(ClassDiagram.class::cast)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Diagrama de classe com erros."));
		// @formatter:on
		return new PlantClassDiagramRepresentationImpl(path, reader, classDiagram);
	}

}
