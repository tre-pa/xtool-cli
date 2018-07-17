package br.xtool.core.diagram.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.Import;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import net.sourceforge.plantuml.cucadiagram.Member;

public abstract class FieldMapper {

	private JavaClassSource javaClass;

	private Member member;

	public FieldMapper() {
		super();
	}

	public void init(JavaClassSource javaClass, Member member) {
		this.javaClass = javaClass;
		this.member = member;
	}

	public abstract void map();

	public String getClassName() {
		return this.javaClass.getName();
	}

	protected String getType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

	protected String getName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	public JavaClassSource getJavaClass() {
		return this.javaClass;
	}

	public void addImport(String importName) {
		this.javaClass.addImport(importName);
	}

	public Import addImport(Class<?> type) {
		return this.javaClass.addImport(type);
	}

}
