package br.xtool.core.diagram.mapper.field;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.JpaFieldMapper;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.cucadiagram.Member;

/**
 * Mapeia os atributo do tipo Long do diagrama de classe
 * 
 * @author jcruz
 *
 */
@Component
@Slf4j
public class LongFieldMapper implements JpaFieldMapper {

	@Override
	public void map(JavaClassSource javaClass, Member member) {
		String fieldName = this.getName(member);
		String fieldType = this.getType(member);
		if (StringUtils.equalsIgnoreCase(fieldType, "Long")) {
			log.info("Gerando atributo 'Long {}' na classe {}", fieldName, javaClass.getName());
			// @formatter:off
			javaClass.addField()
				.setPrivate()
				.setType(Long.class)
				.setName(fieldName);
			// @formatter:on
		}
	}

}
