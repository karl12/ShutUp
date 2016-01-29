package com.formicary.shutup.common;

import org.joda.time.DateTime;

public class VoteEvent {
  private DateTime time = DateTime.now();
  private int cumulativeVotes;
  private Participant speaker;

  public VoteEvent(Participant speaker, int cumulativeVotes) {
    this.speaker = speaker;
    this.cumulativeVotes = cumulativeVotes;
  }

  public DateTime getTime() {
    return time;
  }

  public void setTime(DateTime time) {
    this.time = time;
  }

  public int getCumulativeVotes() {
    return cumulativeVotes;
  }

  public void setCumulativeVotes(int cumulativeVotes) {
    this.cumulativeVotes = cumulativeVotes;
  }

  public Participant getSpeaker() {
    return speaker;
  }

  public void setSpeaker(Participant speaker) {
    this.speaker = speaker;
  }
}
