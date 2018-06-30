package br.xtool.core.representation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.Log;
import br.xtool.core.representation.updater.core.Updatable;
import br.xtool.core.representation.updater.core.UpdateRequest;
import lombok.Getter;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntity extends EClass implements Comparable<EEntity>, Updatable<JavaClassSource> {

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

	@Override
	public int compareTo(EEntity o) {
		return this.getName().compareTo(o.getName());
	}

	public <T extends UpdateRequest<EEntity>> void addUpdate(Consumer<UpdateRequests> updateRequest) {
		Collection<UpdateRequest<EEntity>> requests = new ArrayList<>();
		updateRequest.accept(new UpdateRequests(requests));
		this.commitUpdates(requests);
	}

	private void commitUpdates(Collection<UpdateRequest<EEntity>> updateRequests) {
		Log.print(Log.bold(Log.yellow("\t[~] ")), Log.white(this.getQualifiedName()));
		// @formatter:off
		updateRequests
			.stream()
			.filter(Objects::nonNull)
			.filter(updateRequest -> updateRequest.updatePolicy(this))
			.forEach(updateRequest -> updateRequest.apply(this));
		// @formatter:on
		String javaPath = FilenameUtils.concat(this.springBootProject.getMainDir(), this.getPackage().getDir());
		String javaFile = javaPath.concat("/").concat(this.getName().concat(".java"));
		try (FileWriter fileWriter = new FileWriter(javaFile)) {
			fileWriter.write(this.javaClassSource.toUnformattedString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JavaClassSource getSource() {
		return this.javaClassSource;
	}

	public class UpdateRequests {

		private Collection<UpdateRequest<EEntity>> updateRequests = new ArrayList<>();

		public UpdateRequests(Collection<UpdateRequest<EEntity>> updateRequests) {
			super();
			this.updateRequests = updateRequests;
		}

		public void add(UpdateRequest<EEntity> updateRequest) {
			if (Objects.nonNull(updateRequest)) {
				this.updateRequests.add(updateRequest);
			}
		}

	}

}
