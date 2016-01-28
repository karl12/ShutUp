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

public class ListAsMapDeserialiser extends JsonDeserializer {
  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    Map<String, Participant> participants = new HashMap<>();
    for(JsonNode nodeelement : node) {
      Participant participant = new Participant(nodeelement.get("name").textValue(), nodeelement.get("bored").booleanValue());
      participants.put(participant.getName(), participant);
    }
    return participants;
  }
}
