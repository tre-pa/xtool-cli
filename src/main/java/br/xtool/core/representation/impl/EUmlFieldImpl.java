package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Sets;

import br.xtool.core.representation.EUmlField;
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
		return this.getProperties().stream().anyMatch(prop -> prop.equalsIgnoreCase("notnull"));
	}

	@Override
	public boolean isArray() {
		return Strman.containsAll(memberType(), new String[] { "[", "]" });
	}

	@Override
	public Optional<Integer> getMinArrayLength() {
		if (this.isArray()) {
			Pattern arrayRangePattern = Pattern.compile("(\\d*)\\.\\.\\d*");
			String[] arrayMultiplicity = Strman.between(memberType(), "[", "]");
			Matcher matcher = arrayRangePattern.matcher(StringUtils.join(arrayMultiplicity));
			if (matcher.find()) {
				Integer min = Integer.parseInt(matcher.group(1));
				return Optional.of(min);
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Integer> getMaxArrayLength() {
		if (this.isArray()) {
			String[] arrayMultiplicity = Strman.between(memberType(), "[", "]");
			String multiplicityValue = StringUtils.join(arrayMultiplicity);
			if (NumberUtils.isDigits(multiplicityValue)) return Optional.of(Integer.parseInt(multiplicityValue));
			Matcher matcher = Pattern.compile("\\d*\\.\\.(\\d*)").matcher(multiplicityValue);
			if (matcher.find()) {
				Integer max = Integer.parseInt(matcher.group(1));
				return Optional.of(max);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean hasProperty(String name) {
		return this.getProperties().stream().anyMatch(prop -> prop.equalsIgnoreCase(name));
	}

	@Override
	public boolean hasProperties() {
		return !this.getProperties().isEmpty();
	}

	@Override
	public Set<String> getProperties() {
		if (Strman.containsAll(memberType(), new String[] { "{", "}" })) {
			String[] propertiesBlock = Strman.between(memberType(), "{", "}");
			String[] propertiesItens = StringUtils.split(StringUtils.join(propertiesBlock), ",");
			if (propertiesItens.length > 0) {
				return Sets.newHashSet(propertiesItens);
			}
			return Sets.newHashSet(propertiesBlock[0]);
		}
		return new HashSet<>();
	}

	private String memberName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	private String memberType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

}
