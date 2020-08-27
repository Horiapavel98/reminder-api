package com.horia.reminderapi.sms;

import com.horia.reminderapi.model.Reminder;

public class SleepingReminder implements Runnable {

    private boolean shouldTerminate = false;

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

        new SmsMessageSender().sendMessage(reminder.getReceiverPhoneNumber(), stringBuilder.toString());
    }

    private long getReminderDueTime() {
        return reminder.getDueDate().getTime();
    }

    public void setShouldTerminate(boolean shouldTerminate) {
        this.shouldTerminate = shouldTerminate;
    }

    public Reminder getReminder() {
        return reminder;
    }

    @Override
    public void run() {
        long dueTime = getReminderDueTime();
        try {
            Thread.sleep(dueTime - System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!shouldTerminate) {
            release();
            System.out.println("Thread from " + this.getClass() + " exited successfully: reminder.id=" + reminder.getId());
        } else {
            System.out.println("Thread from " + this.getClass() + " exited");
        }
    }
}
