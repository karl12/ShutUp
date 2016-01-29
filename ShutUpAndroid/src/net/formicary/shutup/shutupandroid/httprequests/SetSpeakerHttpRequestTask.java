package net.formicary.shutup.shutupandroid.httprequests;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SetSpeakerHttpRequestTask extends AsyncTask<Void, Void, ResponseEntity> {

  private final String name;
  private final String host;

  public SetSpeakerHttpRequestTask(String name, String host){
    super();
    this.name = name;
    this.host = host;
  }

  @Override
  @Nullable
  protected ResponseEntity doInBackground(Void... params) {
    try {
      MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      map.add("name", name);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
      restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
      ResponseEntity response = restTemplate.postForEntity(host, request, ResponseEntity.class);
      return response;
    } catch(Exception e) {
      Log.e("Http Request Exception", e.getMessage(), e);
    }
    return null;
  }

  @Override
  protected void onPostExecute(ResponseEntity response){}
}
