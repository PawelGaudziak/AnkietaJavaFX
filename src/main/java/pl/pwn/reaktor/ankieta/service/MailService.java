package pl.pwn.reaktor.ankieta.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    // --- dane do wysyłki maila
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 465;
    private static final String FROM = "javajavadoodoo@gmail.com";
    private static final String PASS = "javadoodoo";

    public void sendMail(String to, String subject, String content) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.auth", true);

        //inicjalizację sesji
        Session session = Session.getDefaultInstance(properties);

        //tworzenie wiadomości
        MimeMessage message = new MimeMessage(session);
        message.setSubject(subject);
        message.setContent(content, "text/plain; charset=UTF-8");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        //ustawienie połączenia ze skrzynką pocztową
        Transport transport = session.getTransport();
        transport.connect(HOST, PORT, FROM, PASS);

        //wysłanie wiadomości
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}