package br.xtool.core.representation.angular;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "path", "component" })
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

	@Override
	public String toString() {
		// @formatter:off
		return "NgRoute [" + (path != null ? "path=" + path + ", " : "") + (redirectTo != null ? "redirectTo=" + redirectTo + ", " : "")
				+ (pathMatch != null ? "pathMatch=" + pathMatch + ", " : "") + (component != null ? "component=" + component + ", " : "")
				+ (children != null ? "children=" + children + ", " : "") + (loadChildren != null ? "loadChildren=" + loadChildren + ", " : "")
				+ (canActivate != null ? "canActivate=" + canActivate : "") + "]";
		// @formatter:on
	}

	public static NgRoute of(NgListRepresentation ngList) {
		NgRoute route = new NgRoute();
		route.path = ngList.getRoutePath();
		route.component = ngList.getName();
		return route;
	}

}
