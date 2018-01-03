package com.contentful.tea.java.utils.http;

import okhttp3.Headers;

public class HttpResponse {
  private final int code;
  private final String fileName;
  private final Headers headers;

  HttpResponse(int code, String fileName, String[] headers) {
    this.code = code;
    this.fileName = fileName;
    this.headers = createHeaders(headers);
  }

  private Headers createHeaders(String[] headers) {
    final Headers.Builder builder = new Headers.Builder();
    for (final String line : headers) {
      builder.add(line);
    }
    return builder.build();
  }

  public int getCode() {
    return code;
  }

  public String getFileName() {
    return fileName;
  }

  public Headers headers() {
    return headers;
  }
}
