package edu.wgu.d387_sample_code.rest;

import com.fasterxml.jackson.datatype.jsr310.ser.ZoneIdSerializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MessageMapping {

    @RequestMapping(path = "/welcome-message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] welcomeMessages() {

        List<String> welcomeMessages = new ArrayList<>();
        ExecutorService ex = Executors.newFixedThreadPool(4);

        ex.execute(() -> {
            try {

                Properties properties = new Properties();
                InputStream inStream = new ClassPathResource("welcome_en.properties").getInputStream();
                properties.load(inStream);

                System.out.println(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                synchronized (welcomeMessages) {
                    welcomeMessages.add(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });

        ex.execute(() -> {
            try {

                Properties properties = new Properties();
                InputStream inStream = new ClassPathResource("welcome_fr.properties").getInputStream();
                properties.load(inStream);

                System.out.println(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                synchronized (welcomeMessages) {
                    welcomeMessages.add(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });

        ex.execute(() -> {
            try {

                Properties properties = new Properties();
                InputStream inStream = new ClassPathResource("welcome_en.properties").getInputStream();
                properties.load(inStream);

                System.out.println(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                synchronized (welcomeMessages) {
                    welcomeMessages.add(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });

        ex.execute(() -> {
            try {

                Properties properties = new Properties();
                InputStream inStream = new ClassPathResource("welcome_fr.properties").getInputStream();
                properties.load(inStream);

                System.out.println(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                synchronized (welcomeMessages) {
                    welcomeMessages.add(properties.getProperty("welcome") + " (run by: " + Thread.currentThread().getName() + ")");
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });
        ex.shutdown();
        try {
            ex.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return welcomeMessages.toArray(new String[welcomeMessages.size()]);
    }

    @RequestMapping(path = "/live-presentation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> livePresentation() {

        Map<String, String> response = new HashMap<>();

        ZonedDateTime LiveDateTimeUTC = ZonedDateTime.of(LocalDateTime.parse("2025-04-01T06:00"), ZoneId.of("Etc/UTC"));
        ZonedDateTime LiveDateTimeET = LiveDateTimeUTC.withZoneSameInstant(ZoneId.of("America/Detroit"));
        ZonedDateTime LiveDateTimeMT = LiveDateTimeUTC.withZoneSameInstant(ZoneId.of("America/Dawson"));

        // DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, h:mm a v");

        response.put("UTC", formatter.format(LiveDateTimeUTC).toString());
        response.put("ET", formatter.format(LiveDateTimeET).toString());
        response.put("MT", formatter.format(LiveDateTimeMT).toString());

        System.out.println(LiveDateTimeUTC);
        System.out.println(LiveDateTimeET);
        System.out.println(LiveDateTimeMT);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
