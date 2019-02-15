package br.xtool.core.template;

import org.springframework.stereotype.Component;

import br.xtool.core.TemplateBuilder;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import strman.Strman;

@Component
public class NgEntityTemplates {
	
	public NgEntityRepresentation newNgEntityRepresentation(SpringBootProjectRepresentation bootProject, EntityRepresentation entity) {
		
		String IMPORT1_TPL = "import {{ type }} from './{{module}}';";
		String IMPORT2_TPL = "import {{ type }} from './enums/{{module}}';";
		
		// @formatter:off
		TemplateBuilder.builder()
			.tplFor(entity.getAttributes(), (tb, attr)-> {
				attr.getJpaRelationship().ifPresent(rel -> {
					//Gera o import dos relacionamentos toOne
					tb.tplIf(rel.isOneToOne() || rel.isManyToOne(), IMPORT1_TPL)
						.put("type", attr.getType().getName())
						.put("module", Strman.toKebabCase(attr.getType().getName()));
					//Gera o import dos relacionamentos toMany
					tb.tplIf(rel.isManyToMany() || rel.isOneToMany(), IMPORT1_TPL)
						.put("type", attr.getType().getTypeArguments().get(0).getName())
						.put("module", attr.getType().getTypeArguments().get(0).getName());
					//Gera o import dos tipos Enums
					tb.tplIf(attr.isEnumField(), IMPORT2_TPL)
						.put("type", attr.getType().getName())
						.put("module", Strman.toKebabCase(attr.getType().getName()));
				});
			});
		// @formatter:on
		return null;
	}

}
