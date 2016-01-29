package net.formicary.shutup.shutupandroid.httprequests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.*;
import com.formicary.shutup.common.Meeting;
import com.formicary.shutup.common.Participant;
import net.formicary.shutup.shutupandroid.R;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HostRefreshHttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

  private final Activity activity;
  private final String host;

  public HostRefreshHttpRequestTask(String host, Activity activity) {
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
     Meeting meeting = (Meeting)response.getBody();
     updateProgressBar(meeting);
    }
  }

  private void updateProgressBar(Meeting meeting) {
    double numberBored = 0;
    Map<String, Participant> participants = meeting.getParticipants();
    for(String name : participants.keySet()) {
      if(participants.get(name).isBored())
        numberBored++;
    }
    
    if(numberBored == 0){
      Button button = (Button) activity.findViewById(R.id.redButtonHost);
      button.setClickable(true);
      button.setBackgroundResource(R.drawable.button1);
      button.setText("Shut Up!");
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
    TextView progressText = (TextView)activity.findViewById(R.id.progressTextHost);
    progressText.setText(message);
    progressText.setTextColor(color);
    ProgressBar progressBar = (ProgressBar)activity.findViewById(R.id.progressBarHost);
    progressBar.setProgress(0);
    progressBar.setMax(100);
    progressBar.setProgress((int)value);
    progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
  }
}

