package com.formicary.shutup.common;

public class Participant {
  private String name;
  private boolean isBored = false;

  public Participant(String name) {
    this.name = name;
  }

  public Participant(String name, boolean isBored) {
    this.name = name;
    this.isBored = isBored;
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
