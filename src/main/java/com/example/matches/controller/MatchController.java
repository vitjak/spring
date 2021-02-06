package com.example.matches.controller;

import com.example.matches.service.MatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MatchController {

    private MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/matches/import")
    public void process(){
        matchService.process();
    }

    @PostMapping("/matches/delete")
    public void wipeDb(){
        matchService.wipeDb();
    }

}