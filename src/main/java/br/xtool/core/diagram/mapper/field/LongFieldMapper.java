package br.xtool.core.diagram.mapper.field;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.Names;
import br.xtool.core.diagram.mapper.FieldMapper;
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
public class LongFieldMapper implements FieldMapper {

	@Override
	public void map(JavaClassSource javaClass, Member member) {
		String fieldName = this.getName(member);
		String fieldType = this.getType(member);
		if (StringUtils.equalsIgnoreCase(fieldType, "Long")) {
			log.info("Gerando atributo 'Long {}' na classe {}", fieldName, javaClass.getName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = javaClass.addField()
				.setPrivate()
				.setType(Long.class)
				.setName(fieldName);
			// @formatter:on
			mapId(javaClass, fieldSource);
		}
	}

	private void mapId(JavaClassSource javaClass, FieldSource<JavaClassSource> fieldSource) {
		if (fieldSource.getName().equals("id")) {
			javaClass.addImport("javax.persistence.Id");
			javaClass.addImport("javax.persistence.GeneratedValue");
			javaClass.addImport("javax.persistence.GenerationType");
			javaClass.addImport("javax.persistence.SequenceGenerator");

			// @formatter:off
			fieldSource.addAnnotation("Id");
			fieldSource.addAnnotation("GeneratedValue")
				.setLiteralValue("strategy", "GenerationType.SEQUENCE")
				.setStringValue("generator", Names.asDBSequence(javaClass.getName()));
			fieldSource.addAnnotation("SequenceGenerator")
				.setLiteralValue("initialValue", "1")
				.setLiteralValue("allocationSize", "1")
				.setStringValue("name", Names.asDBSequence(javaClass.getName()))
				.setStringValue("sequenceName", Names.asDBSequence(javaClass.getName()));
			// @formatter:on
		} else {
			javaClass.addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
