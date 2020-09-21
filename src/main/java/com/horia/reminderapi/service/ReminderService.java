package com.horia.reminderapi.service;

import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.model.response.ApiResponse;

import java.util.List;

public interface ReminderService {

    ApiResponse<?> createReminder(Reminder reminder);

    ApiResponse<?> updateReminder(long id, Reminder reminder);

    ApiResponse<?> getAllReminders();

    ApiResponse<?> getReminderById(long reminderId);

    ApiResponse<?> deleteReminder(long id);
}
