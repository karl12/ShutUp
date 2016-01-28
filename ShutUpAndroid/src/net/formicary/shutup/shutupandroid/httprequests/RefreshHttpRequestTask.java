package net.formicary.shutup.shutupandroid.httprequests;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.formicary.shutup.common.Meeting;
import net.formicary.shutup.shutupandroid.ShutUpApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RefreshHttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

  private final String host;

  public RefreshHttpRequestTask(String host) {
    super();
    this.host = host;
  }

  @Override
  @Nullable
  protected ResponseEntity doInBackground(Void... params) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
      restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      ResponseEntity response = restTemplate.getForEntity(host, Meeting.class);
      return response;
    } catch(Exception e) {
      Log.e("Http Request Exception", e.getMessage(), e);
    }
    return null;
  }

  @Override
  protected void onPostExecute(ResponseEntity response){
    if(response != null && response.getStatusCode().name() == "OK") {
      Toast.makeText(ShutUpApplication.getContext(), "Your vote has been registered", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(ShutUpApplication.getContext(), "Voting failed. You are not logged in.", Toast.LENGTH_SHORT).show();
    }
  }

}
