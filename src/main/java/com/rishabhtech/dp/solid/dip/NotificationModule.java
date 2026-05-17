package com.rishabhtech.dp.solid.dip;

import lombok.RequiredArgsConstructor;

interface NotificationService {
    void sendNotification();
}

//Low-level implementations:
class EmailService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Sending Email: test@test.com");
    }
}

class SmsService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Sending SMS: 12345");
    }
}

//High-level module depends on abstraction:
@RequiredArgsConstructor
public class NotificationModule {

    private final NotificationService notificationService;

    public void notifyUser()
    {
        notificationService.sendNotification();
    }

    public static void main(String[] args) {
        NotificationService emailService = new EmailService();
        NotificationModule notificationModule = new NotificationModule(emailService);
        notificationModule.notifyUser();

        NotificationService smsService = new SmsService();
        notificationModule = new NotificationModule(smsService);
        notificationModule.notifyUser();
    }
}
