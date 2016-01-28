package net.formicary.shutup.shutupandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import net.formicary.shutup.shutupandroid.httprequests.HttpRequestTask;
import net.formicary.shutup.shutupandroid.R;
import net.formicary.shutup.shutupandroid.httprequests.RefreshHttpRequestTask;
import org.springframework.http.ResponseEntity;

public class VoteActivity  extends Activity {

  private final String JOIN_URL = "connect-meeting";
  private final String VOTE_URL = "set-bored";
  private final String REFRESH_URL = "host-connect";
  private final long INTERVAL = 1000;

  private String userName;
  private String host;
  private Handler handler = new Handler();
  private Runnable refresh;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.vote);
    final Button button = (Button) findViewById(R.id.redButton);
    button.setBackgroundResource(R.drawable.button1);

    Bundle b = getIntent().getExtras();
    if(b != null){
      userName = b.getString("userName");
      host = b.getString("host").replace(JOIN_URL, VOTE_URL);
      startRefresh(host.replace(VOTE_URL, REFRESH_URL));
    }

    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        button.setBackgroundResource(R.drawable.button2);
      }
    });
  }

  private void startRefresh(final String host) {
    refresh = new Runnable() {
      @Override
      public void run() {
        new RefreshHttpRequestTask(host).execute();
        handler.postDelayed(refresh, INTERVAL);
      }
    };
    handler.post(refresh);
  }

  public void setBored(View view){
    new ChangeStatusHttpRequest(userName, host).execute();
  }

  private class ChangeStatusHttpRequest extends HttpRequestTask {

    public ChangeStatusHttpRequest(String name, String host) {
      super(name, host);
    }

    @Override
    protected void onPostExecute(ResponseEntity response) {
      if(response != null && response.getStatusCode().name() == "OK") {
        Toast.makeText(getApplicationContext(), "Your vote has been registered", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getApplicationContext(), "Voting failed. You are not logged in.", Toast.LENGTH_SHORT).show();
      }
    }
  }

}
