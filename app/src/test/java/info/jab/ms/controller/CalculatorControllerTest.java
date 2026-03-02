package info.jab.ms.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CalculatorControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CalculatorController()).build();
    }

    @ParameterizedTest(name = "sum({0}, {1}) = {2}")
    @CsvSource({
        " 3,   5,  8",
        "-3,  10,  7",
        " 0,   0,  0",
        "-5,  -5, -10"
    })
    void shouldReturnSumOfTwoIntegers(int a, int b, int expected) throws Exception {
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"a\": %d, \"b\": %d}", a, b)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expected)));
    }

    @ParameterizedTest(name = "missing body returns 400")
    @CsvSource({
        "''",
        "'{\"a\": 3}'"
    })
    void shouldReturnBadRequestWhenBodyIsInvalid(String body) throws Exception {
        mockMvc.perform(post("/api/v1/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }
}
