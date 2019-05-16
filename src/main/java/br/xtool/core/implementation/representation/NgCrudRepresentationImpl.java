package br.xtool.core.implementation.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgRoute;

public class NgCrudRepresentationImpl implements NgCrudRepresentation {

	private NgModuleRepresentation ngModule;

	private NgListRepresentation ngList;

	private NgDetailRepresentation ngDetail;

	private Optional<NgEditRepresentation> ngEdit;

	public NgCrudRepresentationImpl(NgModuleRepresentation ngModule, NgListRepresentation ngList, NgDetailRepresentation ngDetail, NgEditRepresentation ngEdit) {
		super();
		this.ngModule = ngModule;
		this.ngList = ngList;
		this.ngDetail = ngDetail;
		this.ngEdit = Optional.of(ngEdit);
	}

	public NgCrudRepresentationImpl(NgModuleRepresentation ngModule, NgListRepresentation ngList, NgDetailRepresentation ngDetail) {
		super();
		this.ngModule = ngModule;
		this.ngList = ngList;
		this.ngDetail = ngDetail;
		this.ngEdit = Optional.empty();
	}

	@Override
	public NgListRepresentation getList() {
		return this.ngList;
	}

	@Override
	public Optional<NgEditRepresentation> getEdit() {
		return this.ngEdit;
	}

	@Override
	public NgDetailRepresentation getDetail() {
		return this.ngDetail;
	}

	@Override
	public List<NgRoute> genRoute() {
		List<NgRoute> routes = new ArrayList<>();
		routes.add(this.getListRoute());
		this.ngEdit.ifPresent(ngEdit -> routes.add(this.getEditRoute()));
		routes.add(this.getDetailRoute());
		return routes;
	}

	private NgRoute getListRoute() {
		NgRoute route = new NgRoute();
		route.setPath("");
		route.setComponent(this.ngList.getName());
		return route;
	}

	private NgRoute getDetailRoute() {
		NgRoute route = new NgRoute();
		route.setPath(String.format(":id%s", this.getList().getName().replace("ListComponent", "")));
		route.setComponent(this.ngDetail.getName());
		return route;
	}

	private NgRoute getEditRoute() {
		NgRoute route = new NgRoute();
		route.setPath("edit");
		route.setComponent(this.ngEdit.get().getName());
		route.getChildren().add(new NgRoute(""));
		route.getChildren().add(new NgRoute(String.format(":id%s", this.ngEdit.get().getName().replace("EditComponent", ""))));
		return route;
	}

}
