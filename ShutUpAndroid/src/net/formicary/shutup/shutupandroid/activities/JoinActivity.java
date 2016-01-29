package net.formicary.shutup.shutupandroid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.formicary.shutup.common.Meeting;
import net.formicary.shutup.shutupandroid.R;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class JoinActivity extends android.app.Activity {

  private final String HTTP = "http://";
  private final String JOIN_URL = "/api/connect-meeting";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.join);
  }

  public void clientConnect(View view) {

    EditText host = (EditText)findViewById(R.id.host_value);
    EditText name = (EditText)findViewById(R.id.name_value);

    String hostValue = HTTP + host.getText().toString() + JOIN_URL;
    String nameValue = name.getText().toString();

    new ConnectHttpRequest(nameValue, hostValue).execute();
  }

  private class ConnectHttpRequest extends AsyncTask<Void, Void, ResponseEntity> {

    private final String name;
    private final String host;

    public ConnectHttpRequest(String name, String host) {
      super();
      this.name = name;
      this.host = host;
    }

    @Override
    @Nullable
    protected ResponseEntity doInBackground(Void... params) {
      try {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userName", name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity response = restTemplate.postForEntity(host, request, Meeting.class);
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
        if(meeting.getHost().getName().equals(name)){
          Intent i = new Intent(getApplicationContext(), HostActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("userName", name);
          bundle.putString("host", host);
          i.putExtras(bundle);
          startActivity(i);
        } else {
          Intent i = new Intent(getApplicationContext(), VoteActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("userName", name);
          bundle.putString("host", host);
          i.putExtras(bundle);
          startActivity(i);
        }
      } else {
        Toast.makeText(getApplicationContext(), "Login failed. Username already in use.", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
