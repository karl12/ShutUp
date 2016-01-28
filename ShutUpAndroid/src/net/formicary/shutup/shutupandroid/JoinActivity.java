package net.formicary.shutup.shutupandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class JoinActivity extends Activity {

  private final String HTTP = "http://";
  private final String JOIN_URL = "/api/connect-meeting";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.join);
  }

  public void joinMeeting(View view) {

    EditText host = (EditText)findViewById(R.id.host_value);
    EditText name = (EditText)findViewById(R.id.name_value);

    String hostValue = HTTP + host.getText().toString() + JOIN_URL;
    String nameValue = name.getText().toString();

    new HttpRequestTask(nameValue, hostValue).execute();
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

    private final String name;
    private final String host;

    public HttpRequestTask(String name, String host) {
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

        ResponseEntity response = restTemplate.postForEntity(host, request, ResponseEntity.class);
        return response;
      } catch(Exception e) {
        Log.e("JoinActivity", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(ResponseEntity response) {
      if(response != null && response.getStatusCode().name() == "OK") {
        Intent i = new Intent(getApplicationContext(), VoteActivity.class);
        startActivity(i);
      } else {
        Toast.makeText(getApplicationContext(), "Login failed. Username already in use.", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
