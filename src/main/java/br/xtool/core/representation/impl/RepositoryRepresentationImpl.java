package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaPackageRepresentation;
import br.xtool.core.representation.springboot.JpaProjectionRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class RepositoryRepresentationImpl extends JavaInterfaceRepresentationImpl implements RepositoryRepresentation {

	private SpringBootProjectRepresentation springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public RepositoryRepresentationImpl(SpringBootProjectRepresentation springBootProject, JavaInterfaceSource javaInterfaceSource) {
		super(springBootProject, javaInterfaceSource);
		this.springBootProject = springBootProject;
		this.javaInterfaceSource = javaInterfaceSource;
	}

	/**
	 * Nome do Repository
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.javaInterfaceSource.getName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	public JavaPackageRepresentation getJavaPackage() {
		return JavaPackageRepresentationImpl.of(this.javaInterfaceSource.getPackage());
	}

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	@Override
	public EntityRepresentation getTargetEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui entidade JPA associada.", this.getName())));
		// @formatter:on
	}

	@Override
	public JpaProjectionRepresentation getTargetProjection() {
		// @formatter:off
		return this.springBootProject.getProjections().stream()
				.filter(e -> e.getName().equals(this.getTargetEntity().getName().concat("Projection")))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui projeção associada.", this.getName())));
		// @formatter:on
	}

	@Override
	public SpecificationRepresentation getTargetSpecification() {
		// @formatter:off
		return this.springBootProject.getSpecifications().stream()
				.filter(e -> e.getName().equals(this.getTargetEntity().getName().concat("Specification")))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui specification associada.", this.getName())));
		// @formatter:on
	}

}
