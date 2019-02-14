package br.xtool.core;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public abstract class TemplateBuilder {

	public static TemplateBuilderBuilder builder() {
		return new TemplateBuilderBuilder();
	}

	public static class TemplateBuilderBuilder {

		private StringBuilder template = new StringBuilder();

		private JtwigModel model;

		protected TemplateBuilderBuilder() {
			super();
			this.model = JtwigModel.newModel();
		}

		public TemplateBuilderBuilder tpl(String template) {
			this.template.append(template);
			return this;
		}

		public TemplateBuilderBuilder put(String key, Object value) {
			this.model.with(key, value);
			return this;
		}

		public String build() {
			JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(this.template.toString());
			return jtwigTemplate.render(this.model);
		}
	}
}
