package com.example.tracking;

import com.example.tracking.service.TrackingNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackingNumberServiceTest {

    @Autowired
    private TrackingNumberService trackingNumberService;

    @Test
    void testTrackingNumberFormat() {
    	Map<String, String> response = trackingNumberService.generateTrackingNumber(
    		    "MY",
    		    "ID",
    		    1.234,
    		    OffsetDateTime.parse("2024-06-19T10:30:00+08:00"),
    		    UUID.fromString("de619854-b59b-425e-9db4-943979e1bd49"),
    		    "RedBox Logistics",
    		    "redbox-logistics"
    		);

    		String trackingNumber = response.get("tracking_number");


        assertNotNull(trackingNumber);
        assertTrue(trackingNumber.matches("^[A-Z0-9]{1,16}$"), "Tracking number format is invalid: " + trackingNumber);
    }

    @Test
    void testTrackingNumberUniqueness() {
        Set<String> generated = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            Map<String, String> response = trackingNumberService.generateTrackingNumber(
                "MY",
                "ID",
                1.234,
                OffsetDateTime.parse("2024-06-19T10:30:00+08:00"),
                UUID.fromString("de619854-b59b-425e-9db4-943979e1bd49"),
                "RedBox Logistics",
                "redbox-logistics"
            );
            String tn = response.get("tracking_number");
            assertTrue(generated.add(tn), "Duplicate tracking number found: " + tn);
        }
    }

}
