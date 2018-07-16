package br.xtool.core.diagram.mapper;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.FieldMapper;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.cucadiagram.Member;

@Component
@Slf4j
public class LocalDateFieldMapper implements FieldMapper {

	@Override
	public void map(JavaClassSource javaClass, Member member) {
		String fieldName = this.getName(member);
		String fieldType = this.getType(member);
		if (StringUtils.equalsIgnoreCase(fieldType, "LocalDate")) {
			log.info("Gerando atributo 'LocalDate {}' na classe {}", fieldName, javaClass.getName());
			// @formatter:off
			javaClass.addField()
				.setPrivate()
				.setType(LocalDate.class)
				.setName(fieldName);
			// @formatter:on
		}
	}

}
