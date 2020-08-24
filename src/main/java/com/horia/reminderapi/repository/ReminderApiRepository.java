package com.horia.reminderapi.repository;

import com.horia.reminderapi.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderApiRepository extends JpaRepository<Reminder, Long> {

}
