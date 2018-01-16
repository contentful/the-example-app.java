package com.contentful.tea.java.services.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.tea.java.MainController;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class ContentfulTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Before
  public void setup() {
    contentful.setSpaceId("jnzexv31feqf");
    contentful.setPreviewAccessToken("<PREVIEW_TOKEN>");
    contentful.setDeliveryAccessToken("<DELIVERY_TOKEN>");
  }

  @Test
  public void clientRetrievalTest() {
    assertThat(contentful.contentfulPreviewClient).isNull();

    CDAClient client = contentful.getCurrentClient();
    assertThat(client).isNotNull();

    contentful.setApi(Contentful.API_CDA);
    assertThat(contentful.getCurrentClient()).isEqualTo(client);

    contentful.setApi(Contentful.API_CPA);
    assertThat(contentful.getCurrentClient()).isNotEqualTo(client);

    contentful.setApi(Contentful.API_CDA);
    assertThat(contentful.getCurrentClient()).isEqualTo(client);
  }

  @After
  public void tearDown() {
    contentful.reset();
  }

}
