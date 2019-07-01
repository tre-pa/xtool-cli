package br.xtool.core.implementation.representation;

import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Pattern;

import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.PomRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootNgProjectRepresentationImpl extends ProjectRepresentationImpl implements SpringBootNgProjectRepresentation {

	private PomRepresentation pom;

	public SpringBootNgProjectRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getFrameworkVersion() {
		return this.getPom().getParentVersion().get();
	}

	@Override
	public Version getProjectVersion() {
		return Version.valueOf(this.getSpringBootProjectVersion().name().concat("_").concat(this.getAngularProject().getProjectVersion().name()));
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

	private Version getSpringBootProjectVersion() {
		Pattern v1pattern = Pattern.compile("1\\.5\\.\\d+\\.\\w*");
		Pattern v2pattern = Pattern.compile("2\\.\\d+\\.\\d+\\.\\w*");
		if (v1pattern.matcher(getFrameworkVersion()).matches())
			return Version.V1;
		if (v2pattern.matcher(getFrameworkVersion()).matches())
			return Version.V2;
		return Version.NONE;
	}

	@Override
	public Type getProjectType() {
		return Type.SPRINGBOOTNG;
	}

	@Override
	public SpringBootProjectRepresentation getSpringBootProject() {
		return new SpringBootProjectRepresentationImpl(this.getPath().resolve(this.getName().concat("-backend")));
	}

	@Override
	public NgProjectRepresentation getAngularProject() {
		return new NgProjectRepresentationImpl(this.getPath().resolve(this.getName().concat("-frontend")));
	}

	@Override
	public boolean isMultiModule() {
		return true;
	}

}
