package net.formicary.shutup.meeting;

import javax.inject.Singleton;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Singleton
public class MeetingController {

  private Meeting meeting;

  @RequestMapping(method = RequestMethod.POST, path = "/api/create-meeting")
  public @ResponseBody
  ResponseEntity createMeeting(@RequestParam(value = "host") String host){
    meeting = new Meeting(host);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/connect-meeting")
  public Meeting clientConnect(@RequestParam(value = "userName") String userName){
    meeting.getParticipants().add(userName);
    return meeting;
  }
}
