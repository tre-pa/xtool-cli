package br.xtool.core;

import org.apache.commons.lang3.StringUtils;
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
			this.template.append(template.concat("\n"));
			return this;
		}

		public TemplateBuilderBuilder tpl(String template, int tabs) {
			if (!StringUtils.isBlank(template)) {
				this.template.append(StringUtils.repeat("\t", tabs).concat(template.concat("\n")));
			}
			return this;
		}

//		public <T> TemplateBuilderBuilder tplIf(boolean predicate, String tpl) {
//			TemplateBuilderBuilder builder = TemplateBuilder.builder();
//			if (predicate) {
//				builder.model = this.model;
//				this.tpl(builder.tpl(tpl).build());
//			}
//			return this;
//		}

//		public <T, C extends Collection<T>> TemplateBuilderBuilder tplFor(C collection, BiConsumer<TemplateBuilderBuilder, T> biConsumer) {
//			collection.forEach(item -> {
//				TemplateBuilderBuilder builder = TemplateBuilder.builder();
//				builder.put("it", item);
//				biConsumer.accept(builder, item);
//				this.tpl(builder.build());
//			});
//			return this;
//		}

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
