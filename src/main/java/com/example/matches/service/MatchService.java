package com.example.matches.service;

import com.example.matches.model.Match;
import com.example.matches.multithreading.Worker;
import com.example.matches.repository.MatchRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;

@Component
public class MatchService {

    final private static String fileName = "src/main/resources/fo_random.txt";
    final private Map<String, ArrayList<Match>> matches = new HashMap<>();

    //Inject
    private MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void process() {
        List<String> list = new ArrayList<>();

        final long startAll = currentTimeMillis();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = stream
                    .collect(Collectors.toList());

            list.remove(0);
            list.stream()
                    .map(this::parseToMatch)
                    .forEach(this::putToMap);

            System.out.println("Only processing: " + (currentTimeMillis() - startAll));

            final long startInsert = currentTimeMillis();
            matches.values().forEach(matchList -> {
                final Worker worker = new Worker(matchList, matchRepository);
                worker.start();
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Only inserts: " + (currentTimeMillis() - startInsert));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Match parseToMatch(final String line) {
        String[] split = line.split("\\|", 4);
        return new Match(split[0], Long.valueOf(split[1]), split[2], split[3]);
    }

    private void putToMap(final Match match) {
        matches.computeIfPresent(match.getMatchId(), (s, matches) -> {
            matches.add(match);
            return matches;
        });
        matches.computeIfAbsent(match.getMatchId(), k ->  {
            ArrayList<Match> matches = new ArrayList<>();
            matches.add(match);
            return matches;
        });
    }

    public void wipeDb() {
        matchRepository.deleteAll();
    }
}