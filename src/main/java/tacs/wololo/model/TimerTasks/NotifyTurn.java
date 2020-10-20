package tacs.wololo.model.TimerTasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacs.wololo.model.User;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.IGmailService;
import tacs.wololo.services.implementations.GmailService;

import java.util.Optional;
import java.util.TimerTask;

@Service
public class NotifyTurn extends TimerTask {

    String email;

    GmailService gmailService;

    public NotifyTurn() {
    }

    public NotifyTurn(String email) {
        this.email = email;
        this.gmailService = new GmailService();
    }

    public void run() {
        System.out.println("llega aca 3");
        try {
            gmailService.sendEmail(email);
            System.out.println("llega aca 4");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }



    }

}
