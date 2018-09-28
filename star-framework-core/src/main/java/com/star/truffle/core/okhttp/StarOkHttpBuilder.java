package com.star.truffle.core.okhttp;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarOkHttpBuilder {

  private String url;
  private Map<String, String> headers;
  private Map<String, String> params;
  private TypeReference<?> typeReference;
  private Object tag;
  private String body;
}
