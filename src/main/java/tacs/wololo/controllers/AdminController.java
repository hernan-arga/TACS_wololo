package tacs.wololo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tacs.wololo.services.implementations.AdminService;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    @Autowired
    AdminService adminService;

    @GetMapping(path = "/scoreboard/{id}")
    public ResponseEntity<?> scoreboard(@PathVariable(value = "id") Long gameID)
    {
        try
        {
            HashMap<String, Integer> scoreBoard = adminService.getScoreBoard(gameID);
            return ResponseEntity.ok(scoreBoard);

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/statistics/games")
    public ResponseEntity<?> gamesByDate
            (@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
             @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to)
    {
        try
        {
            return ResponseEntity.ok(adminService.getStatisticsByDates(from, to));

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(path = "/statistics/users/{userPlayer}")
    public ResponseEntity<?> user(@PathVariable String userPlayer){
        try
        {
            return ResponseEntity.ok(adminService.getIndividualStatistics(userPlayer));

        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
