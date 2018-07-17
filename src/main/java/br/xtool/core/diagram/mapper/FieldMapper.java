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

	protected String getClassName() {
		return this.javaClass.getName();
	}

	protected String getType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

	protected String getName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	protected JavaClassSource getJavaClass() {
		return this.javaClass;
	}

	protected void addImport(String importName) {
		this.javaClass.addImport(importName);
	}

	protected Import addImport(Class<?> type) {
		return this.javaClass.addImport(type);
	}

	protected boolean isBoolean() {
		return StringUtils.equalsIgnoreCase(this.getType(), "Boolean");
	}

	protected boolean isBigDecimal() {
		return StringUtils.equalsIgnoreCase(this.getType(), "BigDecimal");
	}

	protected boolean isDate() {
		return StringUtils.equalsIgnoreCase(this.getType(), "Date");
	}

	protected boolean isInteger() {
		return StringUtils.equalsIgnoreCase(this.getType(), "Integer");
	}

	protected boolean isLocalDate() {
		return StringUtils.equalsIgnoreCase(this.getType(), "LocalDate");
	}

	protected boolean isLocalDateTime() {
		return StringUtils.equalsIgnoreCase(this.getType(), "LocalDateTime");
	}

	protected boolean isLong() {
		return StringUtils.equalsIgnoreCase(this.getType(), "Long");
	}

	protected boolean isString() {
		return StringUtils.equalsIgnoreCase(this.getType(), "String");
	}
}
