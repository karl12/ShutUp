package net.formicary.shutup.shutupandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import net.formicary.shutup.shutupandroid.R;
import net.formicary.shutup.shutupandroid.httprequests.VoteHttpRequest;
import net.formicary.shutup.shutupandroid.httprequests.RefreshHttpRequestTask;

public class VoteActivity extends Activity {

  private final String JOIN_URL = "connect-meeting";
  private final String VOTE_URL = "set-bored";
  private final String REFRESH_URL = "refresh";
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
      startRefresh(host.replace(VOTE_URL, REFRESH_URL), this);
    }
  }

  private void startRefresh(final String host, final android.app.Activity activity) {
    refresh = new Runnable() {
      @Override
      public void run() {
        new RefreshHttpRequestTask(host, activity).execute();
        handler.postDelayed(refresh, INTERVAL);
      }
    };
    handler.post(refresh);
  }

  public void setBoredParticipant(View view){
    Button button = (Button) view;
    button.setText("");
    button.setBackgroundResource(R.drawable.button2);
    button.setClickable(false);
    new VoteHttpRequest(userName, host).execute();
  }

}
