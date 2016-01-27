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
    return ResponseEntity.ok(meeting);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/api/host-connect")
  public @ResponseBody
  ResponseEntity hostConnect(){
    if(meeting != null) {
      return ResponseEntity.ok(meeting);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/connect-meeting")
  public @ResponseBody
  ResponseEntity clientConnect(@RequestParam(value = "userName") String userName){
    if(meeting.getParticipants().containsKey(userName)) {
      return ResponseEntity.badRequest().build();
    } else {
      meeting.getParticipants().put(userName, new Participant(userName));
      return ResponseEntity.ok().build();
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/set-bored")
  public @ResponseBody
  ResponseEntity setBored(@RequestParam(value = "userName") String userName){
    if(!meeting.getParticipants().containsKey(userName)) {
      return ResponseEntity.badRequest().build();
    } else {
      meeting.getParticipants().get(userName).setBored(true);
      return ResponseEntity.ok().build();
    }
  }
}
