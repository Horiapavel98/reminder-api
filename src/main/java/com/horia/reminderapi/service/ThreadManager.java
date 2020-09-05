package com.horia.reminderapi.service;

import com.horia.reminderapi.exceptions.ThreadNotFoundException;
import com.horia.reminderapi.sms.SleepingReminder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThreadManager {

    private Map<Long, SleepingReminder> threads;

    public ThreadManager() {
        threads = new HashMap<Long, SleepingReminder>();
    }

    public void addOrUpdateThread(long id, SleepingReminder sleepingReminder) {
        if (threads.containsKey(id)) {

            SleepingReminder oldThread = threads.get(id);
            SleepingReminder newThread = new SleepingReminder(oldThread.getReminder());

            oldThread.setShouldTerminate(true);

            threads.put(id, newThread);

            new Thread(newThread).start();
        }else{
            threads.put(id, sleepingReminder);
            new Thread(sleepingReminder).start();
        }
    }

    public void terminateThread(long id) {
        if (threads.containsKey(id)) {
            threads.get(id).setShouldTerminate(true);
            threads.remove(id);
        }else {
            throw new ThreadNotFoundException("Thread with id=" + id + " not found.");
        }
    }
}
