package net.formicary.shutup.meeting;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meeting")
public class Meeting {

  @Id
  private long id;
  private String name;
  private String host;/*
  private List<String> participants = new ArrayList<>();*/

  public Meeting() {
  }

  public Meeting(String name, String host) {
    this.name = name;
    this.host = host;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
/*
  public List<String> getParticipants() {
    return participants;
  }

  public void setParticipants(List<String> participants) {
    this.participants = participants;
  }*/
}
