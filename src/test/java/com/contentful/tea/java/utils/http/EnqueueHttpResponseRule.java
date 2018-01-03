package com.contentful.tea.java.utils.http;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnqueueHttpResponseRule implements MethodRule {
  @Override public Statement apply(Statement statement, FrameworkMethod method, Object o) {
    EnqueueHttpResponse enqueueHttpResponse = method.getAnnotation(EnqueueHttpResponse.class);
    if (enqueueHttpResponse != null) {
      if (!(o instanceof EnqueuedHttpResponseTests)) {
        throw new RuntimeException("Test class must extend "
            + EnqueuedHttpResponseTests.class.getName()
            + " when using @"
            + EnqueueHttpResponse.class.getSimpleName());
      }
      List<HttpResponse> responses = new ArrayList<HttpResponse>();
      responses.addAll(enqueueToTestResponse(enqueueHttpResponse));
      ((EnqueuedHttpResponseTests) o).setResponseQueue(responses);
    }
    return statement;
  }

  private Collection<? extends HttpResponse> enqueueToTestResponse(EnqueueHttpResponse enqueueHttpResponse) {
    final List<HttpResponse> responses
        = new ArrayList<HttpResponse>(
        enqueueHttpResponse.complex().length
            + enqueueHttpResponse.value().length);

    for (final ComplexHttpResponse response : enqueueHttpResponse.complex()) {
      responses.add(new HttpResponse(response.code(), response.fileName(), response.headers()));
    }

    for (final String response : enqueueHttpResponse.defaults()) {
      responses.add(new HttpResponse(200, response, new String[]{}));
    }

    for (final String response : enqueueHttpResponse.value()) {
      responses.add(new HttpResponse(200, response, new String[]{}));
    }

    return responses;
  }
}
