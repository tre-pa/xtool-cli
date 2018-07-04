package br.xtool.core.representation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.Getter;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntity extends EClass implements Comparable<EEntity> {

	private ESpringBootProject springBootProject;

	@Getter(lazy = true)
	private final SortedSet<EAttribute> attributes = buildAttributes();

	@Getter(lazy = true)
	private final SortedSet<EAssociation> associations = buildAssociations();

	public EEntity(ESpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super(javaClassSource);
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	private SortedSet<EAttribute> buildAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
				.map(f -> new EAttribute(this.springBootProject,this, f))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	private SortedSet<EAssociation> buildAssociations() {
		SortedSet<EAssociation> associations = new TreeSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EAttribute::isAssociation)
			.map(EAttribute::getAssociation)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

	/**
	 * Retorna o caminho da classe no projeto.
	 * 
	 * @return
	 */
	public String getPath() {
		return FilenameUtils.concat(this.springBootProject.getPath(), String.format("src/main/java/%s/%s.java", this.getPackage().getDir(), this.getName()));
	}

	public Optional<EAnnotation> addAnnotation(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			String[] annotationTokens = StringUtils.split(qualifiedName, ".");
			String annotationName = annotationTokens[annotationTokens.length - 1];
			if (!javaClassSource.hasAnnotation(annotationName)) {
				AnnotationSource<JavaClassSource> annotationSource = this.javaClassSource.addAnnotation();
				if (!this.javaClassSource.hasImport(qualifiedName)) {
					this.javaClassSource.addImport(qualifiedName);
				}
				annotationSource.setName(annotationName);
				return Optional.of(new EAnnotation(annotationSource));
			}
		}
		return Optional.empty();
	}

	/**
	 * Salva as alteração realizadas no model.
	 */
	public void save() {
		try (FileWriter fw = new FileWriter(new File(this.getPath()))) {
			fw.write(this.javaClassSource.toUnformattedString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(EEntity o) {
		return this.getName().compareTo(o.getName());
	}

}
