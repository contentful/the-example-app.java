package com.contentful.tea.java.models.mappable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.contentful.tea.java.models.mappable.utilities.Reflection.findMappableSuperFields;

public class MappableType {

  public Map<String, Object> toMap() {
    return toMap(null);
  }

  public Map<String, Object> toMap(NullHandler nullHandler) {
    final Map<String, Object> map = new LinkedHashMap<>();
    final List<Field> fields = new ArrayList<>();
    fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
    findMappableSuperFields(this.getClass().getSuperclass(), fields);

    for (final Field field : fields) {
      if (!field.isSynthetic() && !isAllUpperCase(field)) {
        field.setAccessible(true);
        Object value = null;
        try {
          value = field.get(this);
        } catch (IllegalAccessException e) {
          // could not access own field.
        }

        if (value instanceof MappableType) {
          value = ((MappableType) value).toMap(nullHandler);
        } else if (value instanceof MappableType[]) {
          value = mapArray((MappableType[]) value, nullHandler);
        } else if (value instanceof Iterable) {
          value = mapIterable((Iterable) value, nullHandler);
        } else if (value == null && nullHandler != null) {
          value = nullHandler.handleNull(field);
        }

        if (value != null) {
          map.put(field.getName(), value);
        }
      }
    }

    return map;
  }

  private boolean isAllUpperCase(Field field) {
    return field.getName().toUpperCase().equals(field.getName());
  }

  private Map<String, Object>[] mapArray(MappableType[] mappableArray, NullHandler nullHandler) {
    final Map<String, Object>[] array = new Map[mappableArray.length];
    int index = 0;
    for (final MappableType element : mappableArray) {
      array[index++] = element.toMap(nullHandler);
    }
    return array;
  }

  private List mapIterable(Iterable iterable, NullHandler nullHandler) {
    final List list = new ArrayList<>();
    for (final Object object : iterable) {
      if (object instanceof MappableType) {
        final MappableType mappable = (MappableType) object;
        list.add(mappable.toMap(nullHandler));
      } else {
        list.add(object);
      }
    }
    return list;
  }
}
