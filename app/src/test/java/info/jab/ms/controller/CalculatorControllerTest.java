package info.jab.ms.controller;

import info.jab.ms.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Calculator Controller Tests")
class CalculatorControllerTest {

    @Mock
    private CalculatorService calculatorService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CalculatorController controller = new CalculatorController(calculatorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @ParameterizedTest(name = "sum({0}, {1}) should return {2}")
    @CsvSource({
        " 3,   5,  8",
        "-3,  10,  7",
        " 0,   0,  0",
        "-5,  -5, -10",
        "100, 200, 300"
    })
    @DisplayName("Should return correct sum for valid input combinations")
    void should_returnCorrectSum_when_validInputProvided(int a, int b, int expected) throws Exception {
        // Given
        when(calculatorService.sum(a, b)).thenReturn(expected);
        String requestBody = String.format("{\"a\": %d, \"b\": %d}", a, b);

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expected)));

        verify(calculatorService, times(1)).sum(a, b);
    }

    @Test
    @DisplayName("Should return 200 OK with correct content type for valid request")
    void should_returnOkWithCorrectContentType_when_validRequestProvided() throws Exception {
        // Given
        int a = 10;
        int b = 20;
        int expectedSum = 30;
        when(calculatorService.sum(a, b)).thenReturn(expectedSum);
        String requestBody = String.format("{\"a\": %d, \"b\": %d}", a, b);

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("30"));

        verify(calculatorService, times(1)).sum(a, b);
    }

    @ParameterizedTest(name = "Invalid JSON: {0}")
    @ValueSource(strings = {
        "",
        "{}",
        "{\"a\": 3}",
        "{\"b\": 5}",
        "{\"a\": null, \"b\": 5}",
        "{\"a\": 3, \"b\": null}",
        "invalid json",
        "{\"a\": \"not a number\", \"b\": 5}"
    })
    @DisplayName("Should return 400 Bad Request for invalid request body")
    void should_returnBadRequest_when_requestBodyIsInvalid(String invalidBody) throws Exception {
        // Given - invalid request body provided by @ValueSource

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBody))
                .andExpect(status().isBadRequest());

        verify(calculatorService, never()).sum(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Should return 415 Unsupported Media Type when Content-Type is missing")
    void should_returnUnsupportedMediaType_when_contentTypeIsMissing() throws Exception {
        // Given
        String validBody = "{\"a\": 5, \"b\": 10}";

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .content(validBody)) // No Content-Type header
                .andExpect(status().isUnsupportedMediaType());

        verify(calculatorService, never()).sum(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Should return 415 Unsupported Media Type for wrong Content-Type")
    void should_returnUnsupportedMediaType_when_contentTypeIsWrong() throws Exception {
        // Given
        String validBody = "{\"a\": 5, \"b\": 10}";

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.TEXT_PLAIN)
                .content(validBody))
                .andExpect(status().isUnsupportedMediaType());

        verify(calculatorService, never()).sum(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Should handle large integer values correctly")
    void should_handleLargeValues_when_withinIntegerRange() throws Exception {
        // Given
        int largeA = 1000000;
        int largeB = 2000000;
        int expectedSum = 3000000;
        when(calculatorService.sum(largeA, largeB)).thenReturn(expectedSum);
        String requestBody = String.format("{\"a\": %d, \"b\": %d}", largeA, largeB);

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedSum)));

        verify(calculatorService, times(1)).sum(largeA, largeB);
    }

    @Test
    @DisplayName("Should handle negative integer values correctly")
    void should_handleNegativeValues_when_validInputProvided() throws Exception {
        // Given
        int negativeA = -100;
        int negativeB = -200;
        int expectedSum = -300;
        when(calculatorService.sum(negativeA, negativeB)).thenReturn(expectedSum);
        String requestBody = String.format("{\"a\": %d, \"b\": %d}", negativeA, negativeB);

        // When & Then
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedSum)));

        verify(calculatorService, times(1)).sum(negativeA, negativeB);
    }
}