package net.formicary.shutup.shutupandroid.httprequests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.formicary.shutup.common.Meeting;
import com.formicary.shutup.common.Participant;
import net.formicary.shutup.shutupandroid.R;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ParticipantsHttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

  private final Activity activity;
  private final String host;

  public ParticipantsHttpRequestTask(String host, Activity activity) {
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
      updateSpinner((Meeting)response.getBody());
    }
  }

  private void updateSpinner(Meeting meeting) {
    Spinner spinner = (Spinner)activity.findViewById(R.id.spinner);
    List<String> spinnerArray = new ArrayList<>();
    Map<String, Participant> participants = meeting.getParticipants();
    for(String name : participants.keySet()) {
      spinnerArray.add(name);
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, spinnerArray);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }
}

