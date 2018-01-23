package com.contentful.tea.java.services.modelenhancers;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAHttpException;
import com.contentful.java.cda.LocalizedResource;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.contentful.tea.java.services.contentful.Contentful.API_CPA;
import static java.lang.String.format;

@Component
public class AddEditorialFeaturesEnhancer {
  private static final int DEFAULT_RECURSION_DEPTH = 2;

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
    final String api = contentful.getApi();
    try {
      final CDAClient client = contentful
          .setApi(Contentful.API_CDA)
          .getCurrentClient();

      try {
        final CDAEntry publishedEntry = client
            .fetch(CDAEntry.class)
            .include(5)
            .one(currentEntry.id());

        if (areFieldsDifferent(publishedEntry, currentEntry)) {
          base.getMeta().setPendingChanges(true);
        }
      } catch (CDAHttpException exception) {
        if (exception.responseCode() == 404) {
          base.getMeta().setDraft(true);
        } else {
          throw exception;
        }
      }
    } finally {
      contentful.setApi(api);
    }
  }

  private boolean areFieldsDifferent(LocalizedResource publishedEntry, LocalizedResource currentEntry) {
    return areFieldsDifferent(publishedEntry, currentEntry, DEFAULT_RECURSION_DEPTH);
  }

  private boolean areFieldsDifferent(LocalizedResource publishedEntry, LocalizedResource currentEntry, int remainingRecursions) {
    if (remainingRecursions <= 0) {
      return false;
    }

    for (final String key : currentEntry.rawFields().keySet()) {
      final Object publishedValue = publishedEntry.getField(key);
      final Object currentValue = currentEntry.getField(key);

      if (currentValue instanceof LocalizedResource && publishedValue instanceof LocalizedResource) {
        if (areFieldsDifferent(((LocalizedResource) currentValue), ((LocalizedResource) publishedValue), remainingRecursions - 1)) {
          return true;
        }
      } else if (currentValue instanceof List && publishedValue instanceof List) {
        final List currentList = ((List) currentValue);
        final List publishedList = ((List) publishedValue);
        if (currentList.size() != publishedList.size()) {
          return true;
        }

        for (int i = 0; i < currentList.size(); ++i) {
          final LocalizedResource currentItem = (LocalizedResource) currentList.get(i);
          final LocalizedResource publishedItem = (LocalizedResource) publishedList.get(i);

          if (areFieldsDifferent(currentItem, publishedItem, remainingRecursions - 1)) {
            return true;
          }
        }
      } else if (!Objects.equals(publishedValue, currentValue)) {
        return true;
      }
    }

    // no early return, so no changes detected.
    return false;
  }
}
