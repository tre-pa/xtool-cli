package br.xtool.core.diagram.mapper.field;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.JpaFieldMapper;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.cucadiagram.Member;

@Component
@Slf4j
public class LocalDateTimeFieldMapper implements JpaFieldMapper {

	@Override
	public void map(JavaClassSource javaClass, Member member) {
		String fieldName = this.getName(member);
		String fieldType = this.getType(member);
		if (StringUtils.equalsIgnoreCase(fieldType, "LocalDateTime")) {
			log.info("Gerando atributo 'LocalDateTime {}' na classe {}", fieldName, javaClass.getName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = javaClass.addField()
				.setPrivate()
				.setType(LocalDateTime.class)
				.setName(fieldName);
			// @formatter:on
			javaClass.addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
