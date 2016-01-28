package com.formicary.shutup.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ListAsMapDeSerialiser extends JsonDeserializer {
  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    return null;
  }
}
