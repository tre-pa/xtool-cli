package br.xtool.core.diagram.mapper.field;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.cucadiagram.Member;

@Component
@Slf4j
public class BigDecimalFieldMapper implements FieldMapper {

	@Override
	public void map(JavaClassSource javaClass, Member member) {
		String fieldName = this.getName(member);
		String fieldType = this.getType(member);
		if (StringUtils.equalsIgnoreCase(fieldType, "BigDecimal")) {
			log.info("Gerando atributo 'String {}' na classe {}", fieldName, javaClass.getName());
			// @formatter:off
			javaClass.addField()
				.setPrivate()
				.setType(BigDecimal.class)
				.setName(fieldName);
			// @formatter:on
		}
	}

}
