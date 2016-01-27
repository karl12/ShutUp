package net.formicary.shutup.meeting;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MeetingController {

  private final MeetingRepository meetingRepository;

  @Inject
  public MeetingController(MeetingRepository meetingRepository) {
    this.meetingRepository = meetingRepository;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/create-meeting")
  public @ResponseBody
  ResponseEntity createMeeting(@RequestParam(value = "host") String host, @RequestParam(value = "name") String meetingName){
    Meeting meeting = new Meeting(host, meetingName);
    meetingRepository.save(meeting);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(method = RequestMethod.POST, path = "/api/connect-meeting")
  public Meeting clientConnect(@RequestParam(value = "userName") String userName, @RequestParam(value = "name") String meetingName){
    Meeting meeting = meetingRepository.findByName(meetingName);
/*
    meeting.getParticipants().add(userName);
*/
    return meeting;
  }
}
