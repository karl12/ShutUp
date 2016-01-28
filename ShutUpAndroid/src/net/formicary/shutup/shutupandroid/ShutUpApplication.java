package net.formicary.shutup.shutupandroid;

import android.app.Application;
import android.content.Context;

public class ShutUpApplication extends Application{

  private static ShutUpApplication instance;

  public static ShutUpApplication getInstance(){
    return instance;
  }

  public static Context getContext(){
    return instance.getApplicationContext();
  }

  @Override
  public void onCreate(){
    instance = this;
    super.onCreate();
  }
}
