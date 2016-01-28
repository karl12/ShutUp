package net.formicary.shutup.shutupandroid.httprequests;

import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.formicary.shutup.common.Meeting;
import com.formicary.shutup.common.Participant;
import net.formicary.shutup.shutupandroid.R;
import net.formicary.shutup.shutupandroid.ShutUpApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RefreshHttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

  private final Activity activity;
  private final String host;

  public RefreshHttpRequestTask(String host, Activity activity) {
    super();
    this.host = host;
    this.activity = activity;
  }

  @Override
  @Nullable
  protected ResponseEntity doInBackground(Void... params) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
      restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      ResponseEntity response = restTemplate.getForEntity(host, Meeting.class);
      return response;
    } catch(Exception e) {
      Log.e("Http Request Exception", e.getMessage(), e);
    }
    return null;
  }

  @Override
  protected void onPostExecute(ResponseEntity response) {
    if(response != null && response.getStatusCode().name().equals("OK")) {
      updateProgressBar((Meeting)response.getBody());
    }
  }

  private void updateProgressBar(Meeting meeting) {
    double numberBored = 0;
    Map<String, Participant> participants = meeting.getParticipants();
    for(String name : participants.keySet()) {
      if(participants.get(name).isBored())
        numberBored++;
    }
    double value = Math.floor((numberBored / participants.size()) * 100);
    String message;
    int color;
    if (value < 25) {
      message = "Yes, go on.";
      color = Color.GREEN;
    } else if (value < 50) {      
      message = "What are you talking about?";
      color = Color.BLUE;
    } else if (value < 75) {
      message = "Why are you still talking about this?";
      color = Color.YELLOW;
    } else {
      message = "SHUT UP!!";
      color = Color.RED;
    }
    TextView progressText = (TextView)activity.findViewById(R.id.progressText);
    progressText.setText(message);
    progressText.setTextColor(color);
    ProgressBar progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
    progressBar.setProgress(0);
    progressBar.setMax(100);
    progressBar.setProgress((int)value);
    progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

  }
}

