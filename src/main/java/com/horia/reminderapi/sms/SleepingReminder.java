package com.horia.reminderapi.sms;

import com.horia.reminderapi.model.Reminder;

public class SleepingReminder implements Runnable {

    private Reminder reminder;

    public SleepingReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    private void release() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title: ");
        stringBuilder.append(reminder.getName());
        stringBuilder.append(". Description: ");
        stringBuilder.append(reminder.getDescription());

        new SmsMessageSender().sendMessage("+40735211261", stringBuilder.toString());
    }

    private long getReminderDueTime() {
        return reminder.getDueDate().getTime();
    }

    @Override
    public void run() {
        long dueTime = getReminderDueTime();
        try {
            Thread.sleep(dueTime - System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release();
    }
}
