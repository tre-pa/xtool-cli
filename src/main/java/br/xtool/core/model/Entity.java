package br.xtool.core.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;

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

	private SortedSet<Association> associations;

	private List<String> updateInfo = new ArrayList<>();

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
					.map(f -> new Attribute(this.springBootProject,this, f))
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
	public SortedSet<Association> getAssociations() {
		if (this.associations == null) {
			this.associations = new TreeSet<>();
			// @formatter:off
			this.getAttributes().stream()
				.filter(Attribute::isAssociation)
				.map(Attribute::getAssociation)
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
	public void addAttribute(Consumer<FieldSource<JavaClassSource>> action) {
		FieldSource<JavaClassSource> newField = this.javaClassSource.addField();
		action.accept(newField);
		this.updateInfo.add("\t\t       + " + newField.getVisibility() + " " + newField.getType() + " " + newField.getName());
	}

	/**
	 * Adcionar uma anotação a classe.
	 * 
	 * @param action
	 */
	public void addAnnotation(Consumer<AnnotationSource<JavaClassSource>> action) {
		AnnotationSource<JavaClassSource> newAnnotation = this.javaClassSource.addAnnotation();
		action.accept(newAnnotation);
		this.updateInfo.add("\t\t       + " + "@" + newAnnotation.getName());
	}
	
	public void addImport(String importName) {
		this.javaClassSource.addImport(importName);
		this.updateInfo.add("\t\t       + " + "import " + importName);
	}

	public void commitUpdate() {
		String javaPath = FilenameUtils.concat(this.springBootProject.getMainDir(), this.getPackage().getDir());
		String javaFile = javaPath.concat("/").concat(this.getName().concat(".java"));
		try (FileWriter fileWriter = new FileWriter(javaFile)) {
			fileWriter.write(this.javaClassSource.toUnformattedString());
			fileWriter.flush();
			fileWriter.close();
			Log.print(Log.green("\t[UPDATE CLASS] ") + Log.white(this.getQualifiedName().concat(".java")));
			this.updateInfo.forEach(info -> Log.print(info));
			this.updateInfo.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
