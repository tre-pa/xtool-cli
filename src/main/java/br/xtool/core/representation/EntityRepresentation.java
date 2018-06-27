package br.xtool.core.representation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;
import lombok.ToString;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EntityRepresentation implements Comparable<EntityRepresentation> {

	private SpringBootProjectRepresentation springBootProject;

	private JavaClassSource javaClassSource;

	private SortedSet<AttributeRepresentation> attributes;

	private SortedSet<AssociationRepresentation> associations;

	@Deprecated
	private List<String> updateInfo = new ArrayList<>();

	public EntityRepresentation(SpringBootProjectRepresentation springBootProject, JavaClassSource javaClassSource) {
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
	public PackageRepresentation getPackage() {
		return PackageRepresentation.of(javaClassSource.getPackage());
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
	public SortedSet<AttributeRepresentation> getAttributes() {
		if (this.attributes == null) {
			// @formatter:off
			this.attributes = this.javaClassSource.getFields().stream()
					.map(f -> new AttributeRepresentation(this.springBootProject,this, f))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.attributes;
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	public SortedSet<AssociationRepresentation> getAssociations() {
		if (this.associations == null) {
			this.associations = new TreeSet<>();
			// @formatter:off
			this.getAttributes().stream()
				.filter(AttributeRepresentation::isAssociation)
				.map(AttributeRepresentation::getAssociation)
				.forEach(association -> this.associations.add(association.get()));
			// @formatter:on
		}
		return this.associations;
	}

	/**
	 * Verifica se a entidade possui a annotation
	 * 
	 * @param name
	 *            Nome da annotation
	 * @return
	 */
	public boolean hasAnnotation(String name) {
		return this.javaClassSource.hasAnnotation(name);
	}

	/**
	 * Adicionar um atributo a classe.
	 * 
	 * @param action
	 */
	@Deprecated
	public void addAttribute(Consumer<FieldSource<JavaClassSource>> action) {
		FieldSource<JavaClassSource> newField = this.javaClassSource.addField();
		action.accept(newField);
		this.updateInfo.add("\t\t + " + newField.getVisibility() + " " + newField.getType() + " " + newField.getName());
	}

	/**
	 * Adcionar uma anotação a classe.
	 * 
	 * @param action
	 */
	@Deprecated
	public void addAnnotation(Consumer<AnnotationSource<JavaClassSource>> action) {
		AnnotationSource<JavaClassSource> newAnnotation = this.javaClassSource.addAnnotation();
		action.accept(newAnnotation);
		this.updateInfo.add("\t\t + " + "@" + newAnnotation.getName());
	}

	@Deprecated
	public void addImport(String importName) {
		if (!this.javaClassSource.hasImport(importName)) {
			this.javaClassSource.addImport(importName);
			this.updateInfo.add("\t\t + " + "import " + importName);
		}
	}

	@Deprecated
	public void commitUpdate() {
		String javaPath = FilenameUtils.concat(this.springBootProject.getMainDir(), this.getPackage().getDir());
		String javaFile = javaPath.concat("/").concat(this.getName().concat(".java"));
		try (FileWriter fileWriter = new FileWriter(javaFile)) {
			fileWriter.write(this.javaClassSource.toUnformattedString());
			fileWriter.flush();
			fileWriter.close();
			Log.print(Log.green("\t[UPDATE] ") + Log.white(this.getQualifiedName().concat(".java")));
			this.updateInfo.forEach(info -> Log.print(info));
			this.updateInfo.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(EntityRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

}
