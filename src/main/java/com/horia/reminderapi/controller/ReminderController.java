package com.horia.reminderapi.controller;

import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/reminders")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return ResponseEntity.ok().body(reminderService.getAllReminders());
    }

    @GetMapping("/reminders/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(reminderService.getReminderById(id));
        } catch (ResourceNotFoundException resourceNotFoundException) {
            resourceNotFoundException.printStackTrace();
            return new ResponseEntity<>("CANNOT RETRIEVE -- No reminder found with id: " + id,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reminders")
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok().body(this.reminderService.createReminder(reminder));
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable long id,
                                                   @RequestBody Reminder reminder) {
        try {
            return ResponseEntity.ok().body(this.reminderService.updateReminder(id, reminder));
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            resourceNotFoundException.printStackTrace();
            return new ResponseEntity<>("CANNOT UPDATE -- No reminder found with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(this.reminderService.deleteReminder(id));
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            resourceNotFoundException.printStackTrace();
            return new ResponseEntity<>("CANNOT DELETE -- No reminder found with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }
}
