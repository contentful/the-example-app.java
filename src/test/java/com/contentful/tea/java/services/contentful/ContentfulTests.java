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
    assertThat(subject.getSpaceId()).isEqualTo("qz0n5cdakyl9");
    assertThat(subject.getDeliveryAccessToken()).isEqualTo("580d5944194846b690dd89b630a1cb98a0eef6a19b860ef71efc37ee8076ddb8");
    assertThat(subject.getPreviewAccessToken()).isEqualTo("e8fc39d9661c7468d0285a7ff949f7a23539dd2e686fcb7bd84dc01b392d698b");
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
