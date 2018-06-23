package br.xtool.core.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;
import lombok.Getter;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class Entity implements Comparable<Entity> {

	private SpringBootProject springBootProject;

	private JavaClassSource javaClassSource;

	private SortedSet<Attribute> attributes;

	private SortedSet<Attribute> singleAssociations;

	private SortedSet<Attribute> collectionAssociations;

	public Entity(SpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super();
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	public String getName() {
		return javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	public String getQualifiedName() {
		return javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	public Package getPackage() {
		return Package.of(javaClassSource.getPackage());
	}

	/**
	 * Retorna as annotations da classe
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.javaClassSource.getAnnotations();
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	public SortedSet<Attribute> getAttributes() {
		if (this.attributes == null) {
			// @formatter:off
			this.attributes = this.javaClassSource.getFields().stream()
					.map(f -> new Attribute(this.springBootProject, f))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.attributes;
	}

	public SortedSet<Attribute> getSingleAssociations() {
		if (this.singleAssociations == null) {
			// @formatter:off
			this.singleAssociations = this.getAttributes().stream()
					.filter(attr -> attr.isSingleAssociation())
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.singleAssociations;
	}

	public SortedSet<Attribute> getCollectionAssociations() {
		if (this.collectionAssociations == null) {
			// @formatter:off
			this.collectionAssociations = this.getAttributes().stream()
					.filter(attr -> attr.isCollectionAssociation())
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.collectionAssociations;
	}

	public void update(Consumer<JavaClassSource> action) {
		String javaPath = FilenameUtils.concat(this.springBootProject.getMainDir(), this.getPackage().getDir());
		String javaFile = javaPath.concat("/").concat(this.getName().concat(".java"));
		try (FileWriter fileWriter = new FileWriter(javaFile)) {
			action.accept(this.javaClassSource);
			fileWriter.write(this.javaClassSource.toUnformattedString());
			fileWriter.flush();
			fileWriter.close();
			Log.print(Log.green("\tUPDATE ") + Log.white(this.getQualifiedName().concat(".java")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
