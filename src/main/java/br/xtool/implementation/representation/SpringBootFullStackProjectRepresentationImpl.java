package br.xtool.implementation.representation;

import br.xtool.representation.angular.NgProjectRepresentation;
import br.xtool.representation.springboot.PomRepresentation;
import br.xtool.representation.springboot.SpringBootFullStackProjectRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

import java.nio.file.Path;
import java.util.Objects;

public class SpringBootFullStackProjectRepresentationImpl extends ProjectRepresentationImpl implements SpringBootFullStackProjectRepresentation {

	private PomRepresentation pom;

	public SpringBootFullStackProjectRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getVersion() {
		return getSpringBootProject().getVersion();
	}

	@Override
	public String getFrameworkVersion() {
		return getSpringBootProject().getFrameworkVersion() + " - " + getAngularProject().getFrameworkVersion();
	}

	@Override
	public void refresh() {
		this.getSpringBootProject().refresh();
		this.getAngularProject().refresh();
	}

	@Override
	public PomRepresentation getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = PomRepresentationImpl.of(this.getPath().resolve("pom.xml"));
		}
		return this.pom;
	}

	@Override
	public String getType() {
		return "springboot:fullstack";
	}

	@Override
	public SpringBootProjectRepresentation getSpringBootProject() {
		return new SpringBootProjectRepresentationImpl(this.getPath().resolve(this.getName().concat("-backend")));
	}

	@Override
	public NgProjectRepresentation getAngularProject() {
		return new NgProjectRepresentationImpl(this.getPath().resolve(this.getName().concat("-frontend")));
	}

}
