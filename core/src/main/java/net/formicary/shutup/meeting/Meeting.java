package net.formicary.shutup.meeting;

import java.util.ArrayList;
import java.util.List;

public class Meeting {

  private String host;

  private List<String> participants = new ArrayList<>();

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
  public List<String> getParticipants() {
    return participants;
  }

  public void setParticipants(List<String> participants) {
    this.participants = participants;
  }
}
