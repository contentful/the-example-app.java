package com.contentful.tea.java.models;

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
public class SettingsTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Before
  public void setup() {
    settings.setSpaceId("jnzexv31feqf");
    settings.setPreviewAccessToken("<PREVIEW_TOKEN>");
    settings.setDeliveryAccessToken("<DELIVERY_TOKEN>");
  }

  @Test
  public void clientRetrievalTest() {
    assertThat(settings.contentfulPreviewClient).isNull();

    CDAClient client = settings.getCurrentClient();
    assertThat(client).isNotNull();

    settings.setApi(Settings.API_CDA);
    assertThat(settings.getCurrentClient()).isEqualTo(client);

    settings.setApi(Settings.API_CPA);
    assertThat(settings.getCurrentClient()).isNotEqualTo(client);

    settings.setApi(Settings.API_CDA);
    assertThat(settings.getCurrentClient()).isEqualTo(client);
  }

  @After
  public void tearDown() {
    settings.reset();
  }

}
