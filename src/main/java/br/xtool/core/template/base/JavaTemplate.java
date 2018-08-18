package br.xtool.core.template.base;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public abstract class JavaTemplate {

	public static JavaTemplateModel from(String template) {
		return new JavaTemplateModel(template);
	}

	public static class JavaTemplateModel {

		private String template;

		private JtwigModel model;

		public JavaTemplateModel(String template) {
			super();
			this.template = template;
			this.model = JtwigModel.newModel();
		}

		public JavaTemplateModel put(String key, Object value) {
			this.model.with(key, value);
			return this;
		}

		public String build() {
			JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(this.template);
			return jtwigTemplate.render(this.model);
		}
	}
}
