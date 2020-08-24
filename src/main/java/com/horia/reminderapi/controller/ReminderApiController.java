package com.horia.reminderapi.controller;

import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.service.ReminderApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderApiController {

    @Autowired
    private ReminderApiService reminderService;

    @GetMapping("/reminders")
    public ResponseEntity<ApiResponse> getAllReminders() {
        return ResponseEntity.ok().body(reminderService.getAllReminders());
    }

    @GetMapping("/reminders/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(reminderService.getReminderById(id));
    }

    @PostMapping("/reminders")
    public ResponseEntity<ApiResponse> createReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok().body(this.reminderService.createReminder(reminder));
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable long id,
                                                   @RequestBody Reminder reminder)
            throws ResourceNotFoundException {
            return ResponseEntity.ok().body(this.reminderService.updateReminder(id, reminder));
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(this.reminderService.deleteReminder(id));
    }
}
