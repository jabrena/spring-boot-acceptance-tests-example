package info.jab.ms.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import info.jab.ms.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("God Controller Integration Tests")
class GodControllerIT extends BaseIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Should return 200 OK with the computed god sum as a string")
    void should_returnOkWithSum_when_validRequest() throws Exception {
        // Given
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/greek"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("greek-gods.json")));

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/roman"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("roman-gods.json")));

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/nordic"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("nordic-gods.json")));

        // When & Then
        mockMvc.perform(get("/api/v1/gods/sum")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("78179288397447443426"));
    }
}
