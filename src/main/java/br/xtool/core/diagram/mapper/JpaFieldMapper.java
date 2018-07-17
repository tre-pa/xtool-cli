package br.xtool.core.diagram.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import net.sourceforge.plantuml.cucadiagram.Member;

@FunctionalInterface
public interface JpaFieldMapper {

	public void map(JavaClassSource javaClass, Member member);

	default String getType(Member member) {
		return StringUtils.trim(StringUtils.split(member.getDisplay(false), ":")[1]);
	}

	default String getName(Member member) {
		return StringUtils.trim(StringUtils.split(member.getDisplay(false), ":")[0]);
	}
}
