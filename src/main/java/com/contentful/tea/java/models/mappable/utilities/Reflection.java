package com.contentful.tea.java.models.mappable.utilities;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Reflection {
  public static void findMappableSuperFields(Class<?> superclass, List<Field> fields) {
    if (!superclass.equals(Object.class)) {
      final Field[] superFields = superclass.getDeclaredFields();
      fields.addAll(Arrays.asList(superFields));

      findMappableSuperFields(superclass.getSuperclass(), fields);
    }
  }
}
