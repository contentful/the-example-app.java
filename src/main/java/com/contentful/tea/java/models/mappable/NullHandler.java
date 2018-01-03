package com.contentful.tea.java.models.mappable;

import java.lang.reflect.Field;

public interface NullHandler {
  Object handleNull(Field field);
}
