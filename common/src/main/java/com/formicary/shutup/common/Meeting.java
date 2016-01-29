package com.formicary.shutup.common;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Meeting {

  private Participant host;
  private Participant currentSpeaker;
  @JsonSerialize(using = MapAsListSerialiser.class)
  @JsonDeserialize(using = ListAsMapDeSerialiser.class)
  private Map<String, Participant> participants = new HashMap<>();

  @JsonIgnore
  List<VoteEvent> eventLog = new ArrayList<>();

  public Meeting() {

  }

  public Meeting(Participant host) {
    this.host = host;
    this.currentSpeaker = host;
    participants.put(host.getName(), host);
  }

  public Participant getHost() {
    return host;
  }

  public void setHost(Participant host) {
    this.host = host;
  }

  public Map<String, Participant> getParticipants() {
    return participants;
  }

  public void setParticipants(Map<String, Participant> participants) {
    this.participants = participants;
  }

  public List<VoteEvent> getEventLog() {
    return eventLog;
  }

  public void setEventLog(List<VoteEvent> eventLog) {
    this.eventLog = eventLog;
  }

  public Participant getCurrentSpeaker() {
    return currentSpeaker;
  }

  public void setCurrentSpeaker(Participant currentSpeaker) {
    this.currentSpeaker = currentSpeaker;
  }
}
