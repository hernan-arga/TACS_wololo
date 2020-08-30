package tacs.wololo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tacs.wololo.model.User;

@Slf4j
@RestController
@RequestMapping("/api")
public class UsersController
{
	@PostMapping(path = "/users")
	public ResponseEntity<?> crearUsuario(@RequestBody User user) {
        /*log.info("UsersController:  list users");
        Users resource = usersService.saveUser(user);*/
        return ResponseEntity.ok(3);
    }

    @GetMapping(path = "/login")
    public ResponseEntity<?> login(){
	    return ResponseEntity.ok(4);
    }

    @GetMapping(path = "/signUp")
    public ResponseEntity<?> signUp(){
        return ResponseEntity.ok(4);
    }
}
