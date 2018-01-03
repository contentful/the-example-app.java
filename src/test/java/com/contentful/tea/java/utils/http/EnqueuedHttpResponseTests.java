package com.contentful.tea.java.utils.http;

import com.contentful.java.cda.CDAClient;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.LogManager;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.assertThat;

public class EnqueuedHttpResponseTests {
  private static final String DEFAULT_TOKEN = "test_token";
  private static final String DEFAULT_SPACE = "test_space";

  protected CDAClient client;
  protected MockWebServer server;
  protected List<HttpResponse> responseQueue;

  @Rule public EnqueueHttpResponseRule enqueueResponse = new EnqueueHttpResponseRule();

  @Before public void setUp() throws Exception {
    LogManager.getLogManager().reset();
    server = createServer();
    server.start();

    client = createClient();

    if (responseQueue != null) {
      for (HttpResponse response : responseQueue) {
        enqueue(response);
      }
    }
  }

  @After public void tearDown() throws Exception {
    server.shutdown();
  }

  protected CDAClient createClient() {
    return createBuilder()
        .build();
  }

  protected CDAClient createPreviewClient() {
    return createBuilder()
        .preview()
        .setEndpoint(serverUrl())
        .build();
  }

  protected CDAClient.Builder createBuilder() {
    return CDAClient.builder()
        .setSpace(DEFAULT_SPACE)
        .setToken(DEFAULT_TOKEN)
        .setEndpoint(serverUrl());
  }

  protected String serverUrl() {
    return "http://" + server.getHostName() + ":" + server.getPort();
  }

  protected MockWebServer createServer() {
    return new MockWebServer();
  }

  protected void enqueue(HttpResponse response) throws IOException {
    URL resource = getClass().getClassLoader().getResource(response.getFileName());
    assertThat(resource).withFailMessage("File not found: " + response.getFileName()).isNotNull();
    server.enqueue(new MockResponse().setResponseCode(response.getCode())
        .setBody(FileUtils.readFileToString(new File(resource.getFile()), Charset.defaultCharset()))
        .setHeaders(response.headers()));
  }

  public EnqueuedHttpResponseTests setResponseQueue(List<HttpResponse> responseQueue) {
    this.responseQueue = responseQueue;
    return this;
  }
}
