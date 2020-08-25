package com.horia.reminderapi.sms;

import com.horia.reminderapi.exceptions.NullPropertiesException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.util.Properties;

public class SmsMessageSender {

    private String accountSid;
    private String authToken;

    private String phoneNumberFrom;

    private Properties props = null;

    public SmsMessageSender() {
        getProperties();
    }

    private void getProperties() {
        try {
            this.props = new FileBasedConfigParser().loadPropertiesFromResourceFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (props != null) {
            accountSid = props.getProperty("ACCOUNT_SID");
            authToken = props.getProperty("AUTH_TOKEN");
            phoneNumberFrom = props.getProperty("SENDING_PHONE_NO");
        }
    }

    public String sendMessage(String phoneNumberTo, String messageBody) throws NullPropertiesException {
        if (accountSid == null || authToken == null) {
            throw new NullPropertiesException("ACCOUNT_SID or AUTH_TOKEN properties are null");
        }

        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                new PhoneNumber(phoneNumberTo),
                new PhoneNumber(this.phoneNumberFrom),
                messageBody).create();

        return message.getSid();
    }
}
