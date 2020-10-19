package tacs.wololo.services.implementations;

import tacs.wololo.model.Game;
import tacs.wololo.model.User;
import tacs.wololo.services.IGmailService;


import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class GmailService implements IGmailService {


    public static void sendEmail(User user) {
        final String username = "elapuestoelio@gmail.com";
        final String password = "elapuestoelio";
        String submensaje;
        String mensaje;


        submensaje = "Ya es tu turno!!!";
        mensaje = "Tus rivales ya realizaron su turno en Wololo, ahora te toca a vos!";


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject(submensaje);
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
