package net.formicary.shutup.meeting;

public class Participant {
  private String name;
  private boolean isBored = false;

  public Participant(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isBored() {
    return isBored;
  }

  public void setBored(boolean bored) {
    isBored = bored;
  }
}
