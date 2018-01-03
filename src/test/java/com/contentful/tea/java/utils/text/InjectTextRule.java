package com.contentful.tea.java.utils.text;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectTextRule implements MethodRule {

  @Override public Statement apply(Statement statement, FrameworkMethod method, Object o) {
    final InjectText injectText = method.getAnnotation(InjectText.class);
    if (injectText != null) {
      if (!(o instanceof InjectTextBaseTests)) {
        throw new RuntimeException("Test class must extend "
            + InjectTextBaseTests.class.getName()
            + " when using @"
            + InjectText.class.getSimpleName());
      }
      final InjectTextBaseTests tests = (InjectTextBaseTests) o;

      final String markdownFilePath = injectText.value();
      try {
        URL resource = getClass().getClassLoader().getResource(markdownFilePath);
        assertThat(resource).withFailMessage("File not found: " + markdownFilePath).isNotNull();
        tests.injectedText = FileUtils.readFileToString(new File(resource.getFile()), Charset.forName("UTF-8"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return statement;
  }
}
