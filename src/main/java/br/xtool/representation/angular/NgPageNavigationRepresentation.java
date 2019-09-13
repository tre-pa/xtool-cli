package br.xtool.representation.angular;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = { "text", "path" })
@ToString
public class NgPageNavigationRepresentation {

	private String text;

	private String path;

	private String icon;

	// private List<String> roles;
}
