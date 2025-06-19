package com.example.tracking.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TrackingNumberService {

    public Map<String, String> generateTrackingNumber(String origin, String destination, double weight,
                                                      OffsetDateTime createdAt, UUID customerId,
                                                      String customerName, String customerSlug) {
        String base = customerSlug.toUpperCase() + origin + destination;
        String timestamp = Long.toHexString(System.currentTimeMillis());
        String uuidPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        String trackingNumber = (base + timestamp + uuidPart).replaceAll("[^A-Z0-9]", "").substring(0, 16);

        Map<String, String> result = new HashMap<>();
        result.put("tracking_number", trackingNumber);
        result.put("created_at", createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return result;
    }
}
