package tacs.wololo.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tacs.wololo.model.User;
import tacs.wololo.services.implementations.ProvinceService;
import tacs.wololo.services.implementations.UserService;

@Slf4j
@RestController
@RequestMapping("/api")
public class UsersController
{
    @Autowired
    UserService userService;

	@GetMapping(path = "/users")
	public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsersList());
    }

    @GetMapping("/users/me")
    public UserDetails getCurrentUser()
    {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails;
    }
}
