package br.xtool.representation.angular;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = { "path", "component" })
@ToString
@NoArgsConstructor
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

	public NgRoute(String path) {
		super();
		this.path = path;
	}

}
