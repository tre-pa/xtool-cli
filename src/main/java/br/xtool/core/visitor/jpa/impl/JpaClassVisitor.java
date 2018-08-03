package br.xtool.core.visitor.jpa.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.visitor.ClassVisitor;

@Component
public class JpaClassVisitor implements ClassVisitor {

	@Override
	public void accept(EJavaClass javaClass, EUmlClass umlClass) {
		javaClass.addAnnotation(Entity.class);
		javaClass.addAnnotation(DynamicUpdate.class);
		javaClass.addAnnotation(DynamicInsert.class);
		javaClass.addAnnotation(Table.class).setStringValue("name", EJpaEntity.genDBTableName(umlClass.getName()));
	}

}
