package br.xtool.core.representation.angular;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = { "path", "component" })
@ToString
public class NgRoute {

	private String path;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String redirectTo;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String pathMatch;

	/*
	 * FIXME Alterar para tipo NgComponentRepresentation
	 */
	@JsonRawValue
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String component;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<NgRoute> children = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String loadChildren;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> canActivate = new ArrayList<>();

	public static NgRoute of(NgComponentRepresentation ngComponent) {
		NgRoute route = new NgRoute();
		route.path = ngComponent.getRoutePath();
		route.component = ngComponent.getName();
		return route;
	}
	
	public static Optional<NgRoute> findByPath(NgModuleRepresentation ngModule, String path) {
		// @formatter:off
		return ngModule.getRoutes().stream()
				.flatMap(ngRoute -> ngRoute.getChildren().stream())
				.filter(ngRoute -> ngRoute.getPath().equals(path))
				.findFirst();
		// @formatter:on
	}

}
