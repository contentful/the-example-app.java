package com.contentful.tea.java;

import com.contentful.tea.java.markdown.MarkdownParser;
import com.contentful.tea.java.utils.text.InjectText;
import com.contentful.tea.java.utils.text.InjectTextBaseTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class MarkdownManipulatorTests extends InjectTextBaseTests {

  @Autowired
  @SuppressWarnings("unused")
  private MarkdownParser markdownParser;

  @Test
  public void superSimpleTest() {
    assertThat(markdownParser.parse("foobar")).isEqualTo("foobar");
    assertThat(markdownParser.parse("_foobar_")).isEqualTo("<em>foobar</em>");
    assertThat(markdownParser.parse("a\n\n\nb")).isEqualTo("<p>a</p>\n<p>b</p>\n");
  }

  @Test
  @InjectText("markdown/simple.md")
  public void testSimpleMarkdown() {
    assertThat(
        markdownParser.parse(injectedText)
    ).isEqualTo(
        "Switch to the language from English to German by going to the menu item Locale: <code>U.S. English</code> and selecting <code>German</code>."
    );
  }

  @Test
  @InjectText("markdown/complex.md")
  public void testComplexMarkdown() {
    assertThat(
        markdownParser.parse(injectedText)
    ).isEqualTo(
        "<p>A simple Contentful setup consists of a client application reading content, such as this example app, and another application that is writing content, such as the Contentful web app. The client application is reading content by connecting to the Content Delivery API, and the Contentful Web app is writing content by connecting to the Content Mangement API:</p>\n" +
            "<p><img src=\"//images.contentful.com/ft4tkuv7nwl0/3z7ErmBLIccwQkQkuEY0w4/bd438f4b8c540f56fcc76d75c688baf1/minimal-setup.svg\" alt=\"minimal contentful setup\" /></p>\n" +
            "<p>The <em>Contentful web app</em> is a single page application that Contentful provides to help with the most common tasks when managing content:</p>\n" +
            "<ul>\n" +
            "<li>Provide an interface for editors to write content.</li>\n" +
            "<li>Provide an interface for developes to configure a space, model data, define roles and permissions, and set up webhook notifications.</li>\n" +
            "</ul>\n" +
            "<p>As mentioned, the Contentful web app is a client that uses the Content Management API. Therefore, you could replicate the functionality that the Contentful web app provides by making an API call. This is a powerful aspect of an API-first design because it helps you to connect Contentful to third-party systems.</p>\n"
    );
  }

}
