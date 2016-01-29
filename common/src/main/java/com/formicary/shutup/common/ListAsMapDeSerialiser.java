package com.formicary.shutup.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ListAsMapDeSerialiser extends JsonDeserializer {
  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    Map<String, Participant> participants = new HashMap<>();
    for(JsonNode nodeElement : node) {
      Participant participant = new Participant(nodeElement.get("name").textValue(), nodeElement.get("bored").booleanValue());
      participants.put(participant.getName(), participant);
    }
    return participants;
  }
}
