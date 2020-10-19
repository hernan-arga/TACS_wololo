package tacs.wololo.model.TimerTasks;

import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.model.User;
import tacs.wololo.repositories.UserRepository;
import tacs.wololo.services.implementations.GmailService;

import java.util.Optional;
import java.util.TimerTask;

public class NotifyTurn extends TimerTask {

    String player;
    /*@Autowired
    UserRepository userRepository;
    @Autowired
    GmailService gmailService;*/
    public NotifyTurn(String player) {
        this.player= player;
    }

    public void run() {
        System.out.println("llega aca 3");
        /*Optional<User> user = userRepository.findByUsername(player);
        try {
            gmailService.sendEmail(user.get());
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }*/



    }

}
