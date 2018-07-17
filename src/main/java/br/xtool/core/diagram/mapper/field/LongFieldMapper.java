package br.xtool.core.diagram.mapper.field;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.Names;
import br.xtool.core.diagram.mapper.FieldMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapeia os atributo do tipo Long do diagrama de classe
 * 
 * @author jcruz
 *
 */
@Component
@Slf4j
public class LongFieldMapper extends FieldMapper {

	@Override
	public void map() {
		String fieldName = this.getName();
		if (isLong()) {
			log.info("Gerando atributo 'Long {}' na classe {}", fieldName, this.getClassName());
			// @formatter:off
			FieldSource<JavaClassSource> fieldSource = this.getJavaClass().addField()
				.setPrivate()
				.setType(Long.class)
				.setName(fieldName);
			// @formatter:on
			mapId(fieldSource);
		}
	}

	private void mapId(FieldSource<JavaClassSource> fieldSource) {
		if (fieldSource.getName().equals("id")) {
			addImport("javax.persistence.Id");
			addImport("javax.persistence.GeneratedValue");
			addImport("javax.persistence.GenerationType");
			addImport("javax.persistence.SequenceGenerator");

			// @formatter:off
			fieldSource.addAnnotation("Id");
			fieldSource.addAnnotation("GeneratedValue")
				.setLiteralValue("strategy", "GenerationType.SEQUENCE")
				.setStringValue("generator", Names.asDBSequence(this.getJavaClass().getName()));
			fieldSource.addAnnotation("SequenceGenerator")
				.setLiteralValue("initialValue", "1")
				.setLiteralValue("allocationSize", "1")
				.setStringValue("name", Names.asDBSequence(this.getJavaClass().getName()))
				.setStringValue("sequenceName", Names.asDBSequence(this.getJavaClass().getName()));
			// @formatter:on
		} else {
			addImport("javax.persistence.Column");
			fieldSource.addAnnotation("Column");
		}
	}

}
