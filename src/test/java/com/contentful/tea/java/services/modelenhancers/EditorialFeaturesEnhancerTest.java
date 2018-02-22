package com.contentful.tea.java.services.modelenhancers;

import com.contentful.java.cda.CDAContentType;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class EditorialFeaturesEnhancerTest {

  @Autowired
  @SuppressWarnings("unused")
  private EditorialFeaturesEnhancer enhancer;

  @MockBean
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Test
  public void createCorrectDeeplink() {
    given(contentful.getSpaceId()).willReturn("SPACEID");

    final CDAEntry entry = mock(CDAEntry.class);
    final CDAContentType contentType = mock(CDAContentType.class);
    given(contentType.id()).willReturn("CONTENTTYPEID");
    given(entry.contentType()).willReturn(contentType);
    given(entry.id()).willReturn("ENTRYID");

    final String deeplink = enhancer.generateDeeplinkToContentful(entry);

    assertThat(deeplink).isEqualTo("https://app.contentful.com/spaces/SPACEID/entries/ENTRYID?contentTypeId=CONTENTTYPEID");
  }
}