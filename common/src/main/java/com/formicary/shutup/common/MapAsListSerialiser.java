package com.formicary.shutup.common;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MapAsListSerialiser extends JsonSerializer {
  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
    if(value instanceof Map) {
      Map<String, Participant> participants = (Map<String, Participant>)value;
      gen.writeObject(participants.values());
    }else{
      throw new RuntimeJsonMappingException("Value is not a map");
    }
  }
}
