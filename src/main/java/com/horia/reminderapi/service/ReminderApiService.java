package com.horia.reminderapi.service;

import com.horia.reminderapi.model.Reminder;

import java.util.List;

public interface ReminderApiService {

    Reminder createReminder(Reminder reminder);

    Reminder updateReminder(long id, Reminder reminder);

    List<Reminder> getAllReminders();

    Reminder getReminderById(long reminderId);

    Reminder deleteReminder(long id);
}
