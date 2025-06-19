package com.example.tracking.controller;

import com.example.tracking.service.TrackingNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class TrackingNumberController {

    @Autowired
    private TrackingNumberService trackingNumberService;

    @GetMapping("/next-tracking-number")
    public Map<String, String> getNextTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam double weight,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
            @RequestParam UUID customer_id,
            @RequestParam String customer_name,
            @RequestParam String customer_slug) {

        return trackingNumberService.generateTrackingNumber(origin_country_id, destination_country_id, weight, created_at,
                customer_id, customer_name, customer_slug);
    }
}
