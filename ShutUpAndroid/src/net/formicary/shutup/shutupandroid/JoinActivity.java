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

  public void clientConnect(View view) {

    EditText host = (EditText)findViewById(R.id.host_value);
    EditText name = (EditText)findViewById(R.id.name_value);

    String hostValue = HTTP + host.getText().toString() + JOIN_URL;
    String nameValue = name.getText().toString();

    new ConnectHttpRequest(nameValue, hostValue).execute();
  }

  private class ConnectHttpRequest extends HttpRequestTask {

    private final String name;
    private final String host;

    public ConnectHttpRequest(String name, String host) {
      super(name, host);
      this.name = name;
      this.host = host;
    }

    @Override
    protected void onPostExecute(ResponseEntity response) {
      if(response != null && response.getStatusCode().name() == "OK") {
        Intent i = new Intent(getApplicationContext(), VoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", name);
        bundle.putString("host", host);
        i.putExtras(bundle);
        startActivity(i);
      } else {
        Toast.makeText(getApplicationContext(), "Login failed. Username already in use.", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
