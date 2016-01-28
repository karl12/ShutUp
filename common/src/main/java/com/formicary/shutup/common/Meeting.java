package com.formicary.shutup.common;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Meeting {

  private String host;
  @JsonSerialize(using = MapAsListSerialiser.class)
  private Map<String, Participant> participants = new HashMap<>();

  public Meeting() {
  }

  public Meeting(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Map<String, Participant> getParticipants() {
    return participants;
  }

  public void setParticipants(Map<String, Participant> participants) {
    this.participants = participants;
  }
}
