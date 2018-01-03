package com.contentful.tea.java.utils.text;

import com.contentful.tea.java.MainController;

import org.junit.Rule;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainController.class)
public class InjectTextBaseTests {
  @Rule public InjectTextRule injectTextRule = new InjectTextRule();

  protected String injectedText;
}
