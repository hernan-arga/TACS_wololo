package com.bbtutorials.users.controller;

import java.util.Date;
import java.util.List;

import com.bbtutorials.users.entity.EstadoPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bbtutorials.users.entity.Users;
import com.bbtutorials.users.service.UsersService;

import lombok.extern.slf4j.Slf4j;

import javax.xml.ws.RequestWrapper;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping(path = "/home")
    public ResponseEntity<?> Home(){
        return ResponseEntity.ok(4);
    }

    //GET   home/partidas?orderBy={fecha}
    @GetMapping(path = "/home/partidas")
    public ResponseEntity<?> OrdenarPartidasPor(@RequestParam("orderBy") Date fecha){
        return ResponseEntity.ok(4);
    }




}
