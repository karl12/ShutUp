package net.formicary.shutup.shutupandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import org.springframework.http.ResponseEntity;

public class VoteActivity  extends Activity {

  private String userName;
  private String host;

  private final String JOIN_URL = "connect-meeting";
  private final String VOTE_URL = "set-bored";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.vote);

    Bundle b = getIntent().getExtras();
    if(b != null){
      userName = b.getString("userName");
      host = b.getString("host").replace(JOIN_URL, VOTE_URL);
    }
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
