package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = String.format(
                "Notification %s to email %s",
                user.username(),
                user.email()
        );
        String body = String.format(
                "Add a new event to %s",
                user.username()
        );
        pool.submit(() -> send(subject, body, user.email()));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }

    public static void main(String[] args) {
        User userOne = new User("User1", "User1@mail.ru");
        User userTwo = new User("User2", "User2@gmail.com");

        EmailNotification emailNotification = new EmailNotification();

        emailNotification.emailTo(userOne);
        emailNotification.emailTo(userTwo);

        emailNotification.close();
    }
}
