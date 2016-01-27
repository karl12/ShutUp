package net.formicary.shutup.meeting;

import org.springframework.data.repository.CrudRepository;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

  Meeting findByName(String name);
}