package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlMultiplicity;
import net.sourceforge.plantuml.cucadiagram.Member;
import strman.Strman;

public class EUmlFieldImpl implements EUmlField {

	private Member member;

	public EUmlFieldImpl(Member member) {
		super();
		this.member = member;
	}

	@Override
	public String getName() {
		return memberName();
	}

	@Override
	public boolean isId() {
		return StringUtils.equalsIgnoreCase(this.getName(), "id");
	}

	@Override
	public boolean isUnique() {
		return this.getProperties().stream().anyMatch(prop -> prop.equalsIgnoreCase("unique"));
	}

	@Override
	public boolean isNotNull() {
		return this.getProperties().stream().anyMatch(prop -> prop.equalsIgnoreCase("unique"));
	}

	@Override
	public boolean hasProperty(String name) {
		return this.getProperties().stream().anyMatch(prop -> prop.equalsIgnoreCase(name));
	}

	@Override
	public boolean hasMultiplicity() {
		return this.getMultiplicity().isPresent();
	}

	@Override
	public Optional<EUmlMultiplicity> getMultiplicity() {
		String[] multiplicity = Strman.between(memberType(), "[", "]");
		if (multiplicity.length > 0) {
			return Optional.of(new EUmlMultiplicityImpl(StringUtils.join(multiplicity)));
		}
		return Optional.empty();
	}

	@Override
	public boolean hasProperties() {
		return !this.getProperties().isEmpty();
	}

	@Override
	public Set<String> getProperties() {
		return Sets.newHashSet(StringUtils.split(StringUtils.join(Strman.between(memberType(), "{", "}")), ","));
	}

	private String memberName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	private String memberType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

}
