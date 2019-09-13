package br.xtool.implementation.representation;

import java.nio.file.Path;

import br.xtool.representation.angular.NgListRepresentation;
import br.xtool.representation.angular.NgRoute;

public class NgListRepresentationImpl extends NgComponentRepresentationImpl implements NgListRepresentation {

	public NgListRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public NgRoute getDefaultRoute() {
		NgRoute ngRoute = new NgRoute();
		ngRoute.setPath("");
		ngRoute.setComponent(this.getName());
		return ngRoute;
	}

}
