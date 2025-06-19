package com.example.tracking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrackingNumberIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/next-tracking-number";
    }

    @Test
    void testGenerateTrackingNumberSuccess() {
        String url = getBaseUrl() + "?origin_country_id=MY"
                + "&destination_country_id=ID"
                + "&weight=1.234"
                + "&created_at=2024-06-19T10:30:00%2B08:00" // encode '+' as %2B
                + "&customer_id=" + UUID.randomUUID()
                + "&customer_name=RedBox%20Logistics" // encode space as %20
                + "&customer_slug=redbox-logistics";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");

        assertTrue(response.getBody().containsKey("tracking_number"), "Missing tracking_number");
        assertTrue(response.getBody().containsKey("created_at"), "Missing created_at");

        String trackingNumber = (String) response.getBody().get("tracking_number");
        assertTrue(trackingNumber.matches("^[A-Z0-9]{1,16}$"),
                "Tracking number format is invalid: " + trackingNumber);
    }

    @Test
    void testMissingRequiredParamReturnsBadRequest() {
        String url = getBaseUrl() + "?origin_country_id=MY&weight=1.0";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Expected 400 Bad Request");
    }

    @Test
    void testInvalidWeightReturnsBadRequest() {
        String url = getBaseUrl() + "?origin_country_id=MY"
                + "&destination_country_id=ID"
                + "&weight=badvalue"
                + "&created_at=2024-06-19T10:30:00%2B08:00"
                + "&customer_id=" + UUID.randomUUID()
                + "&customer_name=RedBox%20Logistics"
                + "&customer_slug=redbox-logistics";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Expected 400 Bad Request");
    }
}
