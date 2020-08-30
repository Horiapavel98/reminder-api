package com.horia.reminderapi.service;

import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.repository.ReminderApiRepository;
import com.horia.reminderapi.sms.SleepingReminder;
import com.horia.reminderapi.time.FutureTimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReminderApiServiceImpl implements ReminderApiService {

    @Autowired
    private ReminderApiRepository reminderRepository;

    private ThreadManager threadManager = new ThreadManager();

    @Override
    public ApiResponse<?> createReminder(Reminder reminder) {
        if (reminder.getDueDate() == null) {
            reminder.setDueDate(FutureTimeManager.getFutureIncrementedBySeconds(5));
        }
        Reminder reminderToSubmit = reminderRepository.save(reminder);

        System.out.println(reminderToSubmit.getId());

        SleepingReminder sleepingReminder = new SleepingReminder(reminderToSubmit);

        threadManager.addOrUpdateThread(reminderToSubmit.getId(), sleepingReminder);

        return new ApiResponse<>(reminderToSubmit, true,
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
            reminderOld.setReceiverPhoneNumber(reminder.getReceiverPhoneNumber());

            SleepingReminder sleepingReminder = new SleepingReminder(reminderOld);

            threadManager.terminateThread(id);

            threadManager.addOrUpdateThread(id, sleepingReminder);

            return new ApiResponse<>(reminderOld, true,
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

            threadManager.terminateThread(reminderToReturn.getId());

            this.reminderRepository.delete(optionalReminder.get());
            return new ApiResponse<>(reminderToReturn, true,
                    "Reminder deleted successfully", HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Record with id=" + id + " not found");
        }
    }
}
