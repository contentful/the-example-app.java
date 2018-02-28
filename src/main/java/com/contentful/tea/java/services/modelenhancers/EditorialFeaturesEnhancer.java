package com.contentful.tea.java.services.modelenhancers;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAHttpException;
import com.contentful.java.cda.CDAResourceNotFoundException;
import com.contentful.java.cda.LocalizedResource;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import static com.contentful.tea.java.services.contentful.Contentful.API_CPA;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Component
public class EditorialFeaturesEnhancer {
  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  public void enhance(CDAEntry entry, BaseParameter base) {
    final boolean editorialFeatures = settings.areEditorialFeaturesEnabled();
    if (!base.getMeta().hasEditorialFeatures()) {
      base.getMeta().setEditorialFeatures(editorialFeatures);
    }

    if (editorialFeatures
        && contentful.getApi().equals(API_CPA)
        && !base.getMeta().hasPendingChanges()
        && !base.getMeta().isDraft()) {
      updateDraftAndPendingStates(entry, base);
    }

    base
        .getMeta()
        .setDeeplinkToContentful(generateDeeplinkToContentful(entry));
  }

  String generateDeeplinkToContentful(CDAEntry entry) {
    final String spaceId = contentful.getSpaceId();
    final String entryId = entry.id();
    final String contentTypeId = entry.contentType().id();

    return format(Locale.getDefault(),
        "https://app.contentful.com/spaces/%s/entries/%s?contentTypeId=%s",
        spaceId, entryId, contentTypeId);
  }

  private void updateDraftAndPendingStates(CDAEntry currentEntry, BaseParameter base) {
    base.getMeta().setDraft(base.getMeta().isDraft() || isDraft(currentEntry));
    base.getMeta().setPendingChanges(base.getMeta().hasPendingChanges() || isPending(currentEntry));
  }

  public boolean isDraft(CDAEntry previewEntry) {
    final String api = contentful.getApi();
    try {
      final CDAClient client = contentful
          .setApi(Contentful.API_CDA)
          .getCurrentClient();

      final CDAEntry publishedEntry = client
          .fetch(CDAEntry.class)
          .one(previewEntry.id());

      if (publishedEntry == null) {
        return true;
      }
    } catch (CDAResourceNotFoundException exception) {
      return true;
    } catch (CDAHttpException exception) {
      if (exception.responseCode() == 404) {
        return true;
      }
    } catch (Throwable t) {
      return false;
    } finally {
      contentful.setApi(api);
    }

    return false;
  }

  public boolean isPending(CDAEntry previewEntry) {
    final String api = contentful.getApi();
    try {
      final CDAClient client = contentful
          .setApi(Contentful.API_CDA)
          .getCurrentClient();

      final CDAEntry publishedEntry = client
          .fetch(CDAEntry.class)
          .one(previewEntry.id());

      if (isPreviewUpdatedMoreRecently(publishedEntry, previewEntry)) {
        return true;
      }
    } catch (Throwable t) {
      return false;
    } finally {
      contentful.setApi(api);
    }

    return false;
  }

  private boolean isPreviewUpdatedMoreRecently(LocalizedResource publishedEntry, LocalizedResource previewEntry) {
    final LocalDateTime publishedUpdatedAtTime = getPublishedAtTime(publishedEntry);
    final LocalDateTime previewUpdatedAtTime = getPublishedAtTime(previewEntry);

    return previewUpdatedAtTime.compareTo(publishedUpdatedAtTime) > 0;
  }

  private LocalDateTime getPublishedAtTime(LocalizedResource entry) {
    final CharSequence updatedAt = entry.getAttribute("updatedAt");
    final TemporalAccessor accessor = ISO_DATE_TIME.parse(updatedAt);
    final LocalDateTime dateTime = LocalDateTime.from(accessor);
    return dateTime.minus(dateTime.get(ChronoField.NANO_OF_SECOND), ChronoUnit.NANOS);
  }
}
