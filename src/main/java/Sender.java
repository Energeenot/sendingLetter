
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Sender {
    private String username;
    private String password;
    private Properties props;

    public Sender(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));// от кого
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // кому
            message.setSubject(subject);// тема сообщения
            message.setText(text);// текст

            Date date = new Date();
            String str = String.format("Текущая дата и время: %tc", date);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(str, "text/html; charset=utf-8");
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent("Средняя температура 9 градусов цельсия, весь день облачно.", "text/html; charset=utf-8");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\kot_i_komp.jpg"));
            MimeBodyPart attachmentJavaPart = new MimeBodyPart();
            attachmentJavaPart.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\Main.java"));
            MimeBodyPart attachmentJava1Part = new MimeBodyPart();
            attachmentJava1Part.attachFile(new File("C:\\Users\\abram\\IdeaProjects\\sendingLetter\\src\\main\\java\\Sender.java"));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachmentBodyPart);
            multipart.addBodyPart(attachmentJavaPart);
            multipart.addBodyPart(attachmentJava1Part);
            message.setContent(multipart);
            session.setDebug(true);

            Transport.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
