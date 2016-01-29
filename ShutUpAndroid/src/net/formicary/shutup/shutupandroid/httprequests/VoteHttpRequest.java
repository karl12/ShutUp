package net.formicary.shutup.shutupandroid.httprequests;

import org.springframework.http.ResponseEntity;

public class VoteHttpRequest extends HttpRequestTask {

  public VoteHttpRequest(String name, String host) {
    super(name, host);
  }

  @Override
  protected void onPostExecute(ResponseEntity response) {
  }
}
