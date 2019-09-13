package br.xtool.implementation.representation;

import java.util.ArrayList;
import java.util.List;

import br.xtool.representation.angular.NgImportRepresentation;
import lombok.ToString;

@ToString
public class NgImportImplRepresentation implements NgImportRepresentation {

	private List<String> itens = new ArrayList<>();

	private String pathName;

	public NgImportImplRepresentation(List<String> items, String pathName) {
		super();
		this.itens = items;
		this.pathName = pathName;
	}

	@Override
	public List<String> getItems() {
		return this.itens;
	}

	@Override
	public String getPathName() {
		return this.pathName;
	}

}
