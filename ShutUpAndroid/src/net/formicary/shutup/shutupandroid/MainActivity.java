package net.formicary.shutup.shutupandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Called when the activity is first created.
 */
public class MainActivity extends Activity {

  private final String LOCALHOST_URL = "http://10.0.2.2:8083/api/connect-meeting";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.join);
  }

  public void joinMeeting(View view) {

    EditText host = (EditText)findViewById(R.id.host_value);
    EditText name = (EditText)findViewById(R.id.name_value);

    String hostValue = host.getText().toString();
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
    protected ResponseEntity doInBackground(Void... params) {
      try {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userName", name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<>(map, headers);

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        ResponseEntity greeting = restTemplate.postForEntity(LOCALHOST_URL, request, ResponseEntity.class);
        return greeting;
      } catch(Exception e) {
        Log.e("MainActivity", e.getMessage(), e);
      }

      return null;
    }

    @Override
    protected void onPostExecute(ResponseEntity response) {
      Intent i = new Intent(getApplicationContext(), VoteActivity.class);
      startActivity(i);
    }
  }
}
