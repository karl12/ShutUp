package net.formicary.shutup.shutupandroid.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.formicary.shutup.common.Participant;
import net.formicary.shutup.shutupandroid.R;
import net.formicary.shutup.shutupandroid.httprequests.*;

public class HostActivity extends Activity {

  private final String JOIN_URL = "connect-meeting";
  private final String VOTE_URL = "set-bored";
  private final String REFRESH_URL = "refresh";
  private final String RESET_URL = "reset-bored";
  private final String SET_SPEAKER_URL = "set-speaker";
  private final long INTERVAL = 1000;

  private String userName;
  private String host;
  private Handler handler = new Handler();
  private Runnable refresh;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.host);
    Button redButton = (Button)findViewById(R.id.redButtonHost);
    redButton.setBackgroundResource(R.drawable.button1);

    Bundle b = getIntent().getExtras();
    if(b != null) {
      userName = b.getString("userName");
      host = b.getString("host").replace(JOIN_URL, VOTE_URL);
      populateSpinner();
      startRefresh(host.replace(VOTE_URL, REFRESH_URL), this);
    }
  }

  private void populateSpinner() {
    new ParticipantsHttpRequestTask(host.replace(VOTE_URL, REFRESH_URL), this).execute();
  }

  private void startRefresh(final String host, final Activity activity) {
    refresh = new Runnable() {
      @Override
      public void run() {
        new HostRefreshHttpRequestTask(host, activity).execute();
        handler.postDelayed(refresh, INTERVAL);
      }
    };
    handler.post(refresh);
  }

  public void setBoredHost(View view) {
    Button button = (Button)view;
    button.setText("");
    button.setBackgroundResource(R.drawable.button2);
    button.setClickable(false);
    new VoteHttpRequest(userName, host).execute();
  }

  public void moveOn(View view) {
    new ResetScoresHttpRequestTask(host.replace(VOTE_URL, RESET_URL)).execute();
  }

  public void setSpeaker(View view) {
    Spinner spinner = (Spinner)view;
    String selected = spinner.getSelectedItem().toString();
    new SetSpeakerHttpRequestTask(selected, host.replace(VOTE_URL, SET_SPEAKER_URL)).execute();
  }
}
