package com.horia.reminderapi.service;

import com.horia.reminderapi.model.Reminder;
import com.horia.reminderapi.model.Responsible;
import com.horia.reminderapi.model.response.ApiResponse;

import java.util.List;

public interface IReminderService {

    ApiResponse<? extends Responsible> createReminder(Reminder reminder);

    ApiResponse<? extends Responsible> updateReminder(long id, Reminder reminder);

    ApiResponse<List<? extends Responsible>> getAllReminders();

    ApiResponse<? extends Responsible> getReminderById(long reminderId);

    ApiResponse<? extends Responsible> deleteReminder(long id);
}
