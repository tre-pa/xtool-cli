package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;
import br.xtool.core.representation.EUmlRelationship;
import net.sourceforge.plantuml.cucadiagram.Link;

public class EUmlRelationshipImpl implements EUmlRelationship {

	private Link link;

	private Set<EUmlClass> classes;

	public EUmlRelationshipImpl(Set<EUmlClass> classes, Link link) {
		super();
		this.link = link;
		this.classes = classes;
	}

	@Override
	public boolean isBidirectional() {
		return false;
	}

	@Override
	public boolean isUnidirectional() {
		return false;
	}

	@Override
	public EUmlClass getSourceClass() {
		String error = "Classe '%s' não definida no pacote. Insira a definção da classe e os atributos correspondentes no pacote.";
		// @formatter:off
		return this.classes.stream()
				.filter(umlClass -> umlClass.getName().equals(this.link.getEntity1().getDisplay().asStringWithHiddenNewLine()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format(error, this.link.getEntity1().getDisplay().asStringWithHiddenNewLine())));
		// @formatter:on
	}

	@Override
	public EUmlClass getTargetClass() {
		String error = "Classe '%s' não definida no pacote. Insira a definção da classe e os atributos correspondentes no pacote.";
		// @formatter:off
		return this.classes.stream()
				.filter(umlClass -> umlClass.getName().equals(this.link.getEntity2().getDisplay().asStringWithHiddenNewLine()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format(error, this.link.getEntity1().getDisplay().asStringWithHiddenNewLine())));
		// @formatter:on
	}

	@Override
	public Optional<EUmlMultiplicity> getSourceMutiplicity() {
		return null;
	}

	@Override
	public Optional<EUmlMultiplicity> getTargetMultiplicity() {
		return null;
	}

	@Override
	public Optional<String> getSourceRole() {
		return null;
	}

	@Override
	public Optional<String> getTargetRole() {
		return null;
	}

	private String[] getSourceQualifiers() {
		return StringUtils.split(StringUtils.trim(this.link.getQualifier1()));
	}

	private String[] getTargetQualifiers() {
		return StringUtils.split(StringUtils.trim(this.link.getQualifier2()));
	}

}
