package com.example.tracking;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import com.example.tracking.controller.TrackingNumberController;
import com.example.tracking.service.TrackingNumberService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrackingNumberController.class)
class TrackingNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingNumberService trackingNumberService;

    @Test
    void shouldReturnTrackingNumberSuccessfully() throws Exception {
        Map<String, String> responseMap = Map.of(
            "tracking_number", "MYIDABC12345678",
            "created_at", "2024-06-19T10:30:00+08:00"
        );

        given(trackingNumberService.generateTrackingNumber(
            "MY", "ID", 1.234,
            OffsetDateTime.parse("2024-06-19T10:30:00+08:00"),
            UUID.fromString("de619854-b59b-425e-9db4-943979e1bd49"),
            "RedBox Logistics", "redbox-logistics"
        )).willReturn(responseMap);

        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "MY")
                .param("destination_country_id", "ID")
                .param("weight", "1.234")
                .param("created_at", "2024-06-19T10:30:00+08:00")
                .param("customer_id", "de619854-b59b-425e-9db4-943979e1bd49")
                .param("customer_name", "RedBox Logistics")
                .param("customer_slug", "redbox-logistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tracking_number").value("MYIDABC12345678"))
            .andExpect(jsonPath("$.created_at").value("2024-06-19T10:30:00+08:00"));
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredParam() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "MY")
                // Missing destination_country_id
                .param("weight", "1.234")
                .param("created_at", "2024-06-19T10:30:00+08:00")
                .param("customer_id", UUID.randomUUID().toString())
                .param("customer_name", "Test Logistics")
                .param("customer_slug", "test-logistics"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidWeight() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "MY")
                .param("destination_country_id", "ID")
                .param("weight", "invalid")  // Invalid weight
                .param("created_at", "2024-06-19T10:30:00+08:00")
                .param("customer_id", UUID.randomUUID().toString())
                .param("customer_name", "Test Logistics")
                .param("customer_slug", "test-logistics"))
            .andExpect(status().isBadRequest());
    }
}
