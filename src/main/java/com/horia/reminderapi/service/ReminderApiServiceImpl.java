package com.horia.reminderapi.service;

import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.repository.ReminderApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Reminder createReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    @Override
    public Reminder updateReminder(long id, Reminder reminder) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(id);
        if (optionalReminder.isPresent()) {
            Reminder reminderOld = optionalReminder.get();

            reminderOld.setId(id);
            reminderOld.setName(reminder.getName());
            reminderOld.setDescription(reminder.getDescription());
            reminderOld.setDueDate(reminder.getDueDate());

            return reminderOld;
        } else {
            throw new ResourceNotFoundException("Record with id=" + id + " not found");
        }
    }

    @Override
    public List<Reminder> getAllReminders() {
        return this.reminderRepository.findAll();
    }

    @Override
    public Reminder getReminderById(long reminderId) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(reminderId);

        if (optionalReminder.isPresent()) {
            return optionalReminder.get();
        }else {
            throw new ResourceNotFoundException("Record with id=" + reminderId + " not found");
        }
    }

    @Override
    public Reminder deleteReminder(long id) throws ResourceNotFoundException {
        Optional<Reminder> optionalReminder = this.reminderRepository.findById(id);

        if (optionalReminder.isPresent()) {
            Reminder reminderToReturn = optionalReminder.get();
            this.reminderRepository.delete(optionalReminder.get());
            return reminderToReturn;
        }else {
            throw new ResourceNotFoundException("Record with id=" + id + " not found");
        }
    }
}
