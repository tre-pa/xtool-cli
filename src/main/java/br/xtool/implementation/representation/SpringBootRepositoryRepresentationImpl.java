package br.xtool.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.representation.springboot.JpaEntityRepresentation;
import br.xtool.representation.springboot.JavaPackageRepresentation;
import br.xtool.representation.springboot.SpringBootProjectionRepresentation;
import br.xtool.representation.springboot.SpringBootRepositoryRepresentation;
import br.xtool.representation.springboot.SpringBooSpecificationRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class SpringBootRepositoryRepresentationImpl extends JavaInterfaceRepresentationImpl implements SpringBootRepositoryRepresentation {

	private SpringBootProjectRepresentation springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public SpringBootRepositoryRepresentationImpl(SpringBootProjectRepresentation springBootProject, JavaInterfaceSource javaInterfaceSource) {
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
	public JpaEntityRepresentation getTargetEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui entidade JPA associada.", this.getName())));
		// @formatter:on
	}

	@Override
	public SpringBootProjectionRepresentation getTargetProjection() {
		// @formatter:off
		return this.springBootProject.getProjections().stream()
				.filter(e -> e.getName().equals(this.getTargetEntity().getName().concat("Projection")))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui projeção associada.", this.getName())));
		// @formatter:on
	}

	@Override
	public SpringBooSpecificationRepresentation getTargetSpecification() {
		// @formatter:off
		return this.springBootProject.getSpecifications().stream()
				.filter(e -> e.getName().equals(this.getTargetEntity().getName().concat("Specification")))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui specification associada.", this.getName())));
		// @formatter:on
	}

}
