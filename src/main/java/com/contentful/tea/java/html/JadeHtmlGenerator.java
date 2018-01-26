package com.contentful.tea.java.html;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;

import static java.lang.String.format;
import static java.util.Locale.getDefault;

@Component
@SuppressWarnings("unused")
public class JadeHtmlGenerator {
  private static final String BASE_PATH = "src/main/resources/templates";
  private final JadeConfiguration config;
  private final String basePath;

  public JadeHtmlGenerator(boolean prettyPrint, String basePath) {
    config = new JadeConfiguration();
    config.setPrettyPrint(prettyPrint);

    this.basePath = basePath;
  }

  public JadeHtmlGenerator() {
    this(false, BASE_PATH);
  }

  public String generate(String templateFileName, Map<String, Object> templateMappings) {
    final String path = format(getDefault(), "%s/%s", basePath, templateFileName);
    try {
      final JadeTemplate template = config.getTemplate(path);
      return config.renderTemplate(template, templateMappings);
    } catch (IOException e) {
      throw new IllegalStateException(format("Cannot find template '%s' in '%s'.", templateFileName, path), e);
    }

  }
}
