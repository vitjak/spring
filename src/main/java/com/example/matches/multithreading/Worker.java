package com.example.matches.multithreading;


import com.example.matches.model.Match;
import com.example.matches.repository.MatchRepository;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

import static java.lang.System.currentTimeMillis;

public class Worker extends Thread {

    @Getter
    private final List<Match> matchList;
    @Getter
    private final MatchRepository repository;

    public Worker(final List<Match> matchList, final MatchRepository repository) {
        this.matchList = matchList;
        this.repository = repository;
    }

    public void run() {
        sort();
        insertIntoDatabase();
    }

    private void insertIntoDatabase() {
        getMatchList().forEach(s -> {
            s.setData_insert(currentTimeMillis());
        });
        repository.saveAll(getMatchList());
    }

    private void sort() {
        Collections.sort(getMatchList());
    }
}