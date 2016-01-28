package net.formicary.shutup.shutupandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Called when the activity is first created.
 */
public class MainActivity extends Activity {

  private final String LOCALHOST_URL = "http://localhost:8083";

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
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity greeting = restTemplate.postForEntity(LOCALHOST_URL + "/api/connect-meeting", name, ResponseEntity.class);
        return greeting;
      } catch(Exception e) {
        Log.e("MainActivity", e.getMessage(), e);
      }

      return null;
    }

    @Override
    protected void onPostExecute(ResponseEntity greeting) {
      Intent i = new Intent(getApplicationContext(), VoteActivity.class);
      startActivity(i);
    }
  }
}
