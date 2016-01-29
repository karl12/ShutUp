package net.formicary.shutup.meeting;

import java.util.Map;
import javax.inject.Singleton;

import com.formicary.shutup.common.Meeting;
import com.formicary.shutup.common.Participant;
import com.formicary.shutup.common.VoteEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Singleton
public class MeetingController {

  private Meeting meeting;

  @RequestMapping(method = RequestMethod.POST, path = "/api/create-meeting")
  public @ResponseBody
  ResponseEntity createMeeting(@RequestParam(value = "host") String host){
    meeting = new Meeting(new Participant(host));
    return ResponseEntity.ok(meeting);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/api/refresh")
  public
  @ResponseBody
  ResponseEntity refresh() {
    if(meeting != null) {
      return ResponseEntity.ok(meeting);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/connect-meeting")
  public @ResponseBody
  ResponseEntity connect(@RequestParam(value = "userName") String userName){
    if(meeting.getParticipants().containsKey(userName) && !meeting.getHost().getName().equals(userName)) {
      return ResponseEntity.badRequest().build();
    } else if (!meeting.getHost().getName().equals(userName)) {
      meeting.getParticipants().put(userName, new Participant(userName));
    }
      return ResponseEntity.ok(meeting);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/set-bored")
  public @ResponseBody
  ResponseEntity setBored(@RequestParam(value = "userName") String userName){
    if(!meeting.getParticipants().containsKey(userName)) {
      return ResponseEntity.badRequest().build();
    } else {
      meeting.getParticipants().get(userName).setBored(true);
      int totalVotes = 0;
      for(Map.Entry<String, Participant> participant : meeting.getParticipants().entrySet()) {
        if(participant.getValue().isBored()) totalVotes++;
      }
      meeting.getEventLog().add(new VoteEvent(meeting.getCurrentSpeaker(), totalVotes));
      return ResponseEntity.ok().build();
    }
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/set-speaker")
  public @ResponseBody ResponseEntity setSpeaker(@RequestParam(value = "name") String name) {
    resetScores();
    Participant participant = meeting.getParticipants().get(name);
    meeting.setCurrentSpeaker(participant);
    meeting.getEventLog().add(new VoteEvent(participant, 0));
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/reset-bored")
  public  @ResponseBody  ResponseEntity resetScores() {
    int totalScore = 0;
    for(Map.Entry<String, Participant> entry : meeting.getParticipants().entrySet()) {
      if(entry.getValue().isBored()) totalScore++;
      entry.getValue().setBored(false);
    }
    if(((float)totalScore / meeting.getParticipants().size()) > 0.75f) {
      meeting.getCurrentSpeaker().shutup();
    }
    return ResponseEntity.ok().build();
  }
}
