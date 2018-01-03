package com.contentful.tea.java.html;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;

@Component
@SuppressWarnings("unused")
public class JadeHtmlGenerator {
  private final JadeConfiguration config;

  public JadeHtmlGenerator(boolean prettyPrint) {
    config = new JadeConfiguration();
    config.setPrettyPrint(prettyPrint);
  }

  public JadeHtmlGenerator() {
    this(true);
  }

  public String generate(String templateFileName, Map<String, Object> templateMappings) throws IOException {
    final URL resource = getClass().getClassLoader().getResource(templateFileName);
    if (resource != null) {
      templateFileName = resource.getFile();

      final JadeTemplate template = config.getTemplate(templateFileName);
      return config.renderTemplate(template, templateMappings);
    } else {
      throw new FileNotFoundException(String.format("%s not found", templateFileName));
    }
  }
}
