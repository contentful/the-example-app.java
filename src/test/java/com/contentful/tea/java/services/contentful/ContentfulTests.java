package com.contentful.tea.java.services.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.Before;
import org.junit.Test;

import static com.contentful.tea.java.services.contentful.Contentful.API_CDA;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class ContentfulTests extends EnqueuedHttpResponseTests {

  private Contentful contentful;

  @Before
  public void setup() {
    contentful = new Contentful()
        .setSpaceId("<SPACE_ID>")
        .setPreviewAccessToken("<PREVIEW_TOKEN>")
        .setDeliveryAccessToken("<DELIVERY_TOKEN>")
    ;
  }

  @Test
  public void differentApiReturnDifferentClients() {
    assertThat(contentful.contentfulPreviewClient).isNull();

    final CDAClient client = contentful.getCurrentClient();
    assertThat(client).isNotNull();

    contentful.setApi(API_CDA);
    assertThat(contentful.getCurrentClient()).isEqualTo(client);

    contentful.setApi(Contentful.API_CPA);
    assertThat(contentful.getCurrentClient()).isNotEqualTo(client);

    contentful.setApi(API_CDA);
    assertThat(contentful.getCurrentClient()).isEqualTo(client);
  }

  @Test
  public void canLoad() {
    final Contentful subject = new Contentful().load(contentful);

    assertThat(subject.toString()).isEqualTo(contentful.toString());
  }

  @Test
  public void canLoadDefaults() {
    final Contentful subject = new Contentful().loadFromPreferences();

    assertThat(subject.getApi()).isEqualTo(API_CDA);
    assertThat(subject.getSpaceId()).isEqualTo("jnzexv31feqf");
    assertThat(subject.getDeliveryAccessToken()).isEqualTo("7c1c321a528a25c351c1ac5f53e6ddc6bcce0712ecebec60817f53b35dd3c42b");
    assertThat(subject.getPreviewAccessToken()).isEqualTo("4310226db935f0e9b6b34fb9ce6611e2061abe1aab5297fa25bd52af5caa531a");
  }

  @Test
  public void canSave() {
    final Contentful subject = contentful.save();

    assertThat(subject.getSpaceId()).isEqualTo("<SPACE_ID>");
    assertThat(subject.getPreviewAccessToken()).isEqualTo("<PREVIEW_TOKEN>");
    assertThat(subject.getDeliveryAccessToken()).isEqualTo("<DELIVERY_TOKEN>");
    assertThat(subject.getApi()).isEqualTo(API_CDA);
  }

  @Test
  public void canReset() {
    contentful.reset();

    assertThat(contentful.getApi()).isEqualTo(API_CDA);
    assertThat(contentful.getSpaceId()).isNull();
    assertThat(contentful.getPreviewAccessToken()).isNull();
    assertThat(contentful.getDeliveryAccessToken()).isNull();
  }
}
