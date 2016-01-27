package net.formicary.shutup.shutupandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends Activity {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.join);

    final Button redButton = (Button) findViewById(R.id.join_button);
    redButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {

       EditText host = (EditText) findViewById(R.id.host_value);
       EditText name = (EditText) findViewById(R.id.name_value);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());


      }
    });

  }
}
