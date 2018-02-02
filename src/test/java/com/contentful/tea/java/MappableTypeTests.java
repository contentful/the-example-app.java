package com.contentful.tea.java;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class MappableTypeTests {
  @Test
  public void simpleMappable() {
    Map<String, Object> nestedMap = new LinkedHashMap<>();
    nestedMap.put("x", 12);
    nestedMap.put("y", 24.0);

    assertThat(
        new MappableType() {
          private int i = 42;
          private String s = "string";
          private MappableType nested = new MappableType() {
            private int x = 12;
            private double y = 24.0;
          };
        }.toMap()
    )
        .containsExactly(
            entry("i", 42),
            entry("s", "string"),
            entry("nested", nestedMap)
        );
  }

  @Test
  public void mappableWithArrayElementsTest() {
    final Map<String, Object> actual = new MappableType() {
      private String[] array = new String[]{"a", "b", "c"};
    }.toMap();

    assertThat(actual).containsOnlyKeys("array");
    final String[] array = (String[]) actual.get("array");
    assertThat(array.length).isEqualTo(3);
    assertThat(array[0]).isEqualTo("a");
    assertThat(array[1]).isEqualTo("b");
    assertThat(array[2]).isEqualTo("c");
  }

  @Test
  public void mappableWithArrayContainingMappableElementsTest() {
    final Map<String, Object> actual = new MappableType() {
      private final MappableType[] array = new MappableType[]{
          new MappableType() {
            int x = 12;
          },
          new MappableType() {
            int y = 13;
          },
          new MappableType() {
            int z = 14;
          }
      };
    }.toMap();

    assertThat(actual).containsKey("array");

    final Object arrayObject = actual.get("array");
    assertThat(arrayObject).isInstanceOf(Map[].class);

    final Map<String, Object>[] array = (Map[]) arrayObject;
    assertThat(array[0].get("x")).isEqualTo(12);
    assertThat(array[1].get("y")).isEqualTo(13);
    assertThat(array[2].get("z")).isEqualTo(14);
  }

  @Test
  public void mappableWithListContainingMappableElementsTest() {
    final Map<String, Object> actual = new MappableType() {
      private final List<MappableType> list = Arrays.asList(
          new MappableType() {
            int x = 12;
          },
          new MappableType() {
            int y = 13;
          },
          new MappableType() {
            int z = 14;
          }
      );
    }.toMap();


    assertThat(actual).containsKey("list");

    final Object listObject = actual.get("list");
    assertThat(listObject).isInstanceOf(List.class);

    final List<Map<String, Object>> list = (List<Map<String, Object>>) listObject;
    assertThat(list.get(0).get("x")).isEqualTo(12);
    assertThat(list.get(1).get("y")).isEqualTo(13);
    assertThat(list.get(2).get("z")).isEqualTo(14);
  }

  @Test
  public void simpleLayoutParameter() {
    final BaseParameter base = new BaseParameter();
    base.getMeta()
        .setTitle("foo");

    final Map<String, Object> map = base.toMap();
    assertThat(map).containsKeys("api", "locales", "meta");
    assertThat(((Map) map.get("meta")).get("title")).isEqualTo("foo");
  }
}
