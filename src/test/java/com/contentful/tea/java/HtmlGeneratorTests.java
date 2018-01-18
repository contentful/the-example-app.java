package com.contentful.tea.java;

import com.contentful.tea.java.html.JadeHtmlGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlGeneratorTests {

  private JadeHtmlGenerator generator;

  @Before
  public void setup() {
    generator = new JadeHtmlGenerator(false, "src/test/resources");
  }

  @Test
  public void testSimpleHashMapGeneration() throws Exception {
    final HashMap<String, Object> parameter = new HashMap<>();
    parameter.put("title", "my fancy title");
    parameter.put("text", "my fancy text");
    assertThat(
        generator.generate("jade/input.jade", parameter)
    ).isEqualTo(
        "<!DOCTYPE html><html><head><title>my fancy title</title></head><body><p>my fancy text</p></body></html>"
    );
  }

  @Test
  public void testSimpleObjectGeneration() throws Exception {
    final Model parameter = new Model("my fancy title", "my fancy text");
    assertThat(
        generator.generate("jade/input.jade", parameter)
    ).isEqualTo(
        "<!DOCTYPE html><html><head><title>my fancy title</title></head><body><p>my fancy text</p></body></html>"
    );
  }

  private static class Model extends HashMap<String, Object> {
    Model(String title, String text) {
      put("title", title);
      put("text", text);
    }
  }
}
