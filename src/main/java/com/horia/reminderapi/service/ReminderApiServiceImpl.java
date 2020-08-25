package com.horia.reminderapi.service;

import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.repository.ReminderApiRepository;
import com.horia.reminderapi.sms.SleepingReminder;
import com.horia.reminderapi.sms.SmsMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReminderApiServiceImpl implements ReminderApiService {

    @Autowired
    private ReminderApiRepository reminderRepository;

    @Override
    public ApiResponse<?> createReminder(Reminder reminder) {
        Thread reminderThread = new Thread(new SleepingReminder(reminder));
        reminderThread.start();
        // Name that thread for making it killable
        // reminderThread.setName(reminder.getId().toString());
        return new ApiResponse<>(reminderRepository.save(reminder), true,
                "Reminder created successfully", HttpStatus.OK);
    }

    @Override
    public ApiResponse<?> updateReminder(long id, Reminder reminder) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(id);
        if (optionalReminder.isPresent()) {
            Reminder reminderOld = optionalReminder.get();

            reminderOld.setId(id);
            reminderOld.setName(reminder.getName());
            reminderOld.setDescription(reminder.getDescription());
            reminderOld.setDueDate(reminder.getDueDate());

            return new ApiResponse<>(reminder, true,
                    "Reminder updated successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Record with id=" + id + " not found");
        }
    }

    @Override
    public ApiResponse<?> getAllReminders() {
        List<Reminder> reminders = this.reminderRepository.findAll();

        if (reminders.isEmpty()) {
            return new ApiResponse<>(reminders, true,
                    "No reminders recorded, empty database", HttpStatus.OK);
        }

        return new ApiResponse<>(this.reminderRepository.findAll(), true,
                "All reminders are now retrieved", HttpStatus.OK);
    }

    @Override
    public ApiResponse<?> getReminderById(long reminderId) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(reminderId);

        if (optionalReminder.isPresent()) {
            Reminder reminderToReturn = optionalReminder.get();
            return new ApiResponse<>(reminderToReturn, true,
                    "Reminder retrieved successfully", HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Record with id=" + reminderId + " not found");
        }
    }

    @Override
    public ApiResponse<?> deleteReminder(long id) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(id);

        if (optionalReminder.isPresent()) {
            Reminder reminderToReturn = optionalReminder.get();
            this.reminderRepository.delete(optionalReminder.get());
            return new ApiResponse<>(reminderToReturn, true,
                    "Reminder deleted successfully", HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Record with id=" + id + " not found");
        }
    }
}
