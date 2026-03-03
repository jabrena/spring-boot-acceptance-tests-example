package info.jab.ms.service;

import info.jab.ms.client.GodApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("God Service Tests")
class GodServiceTest {

    @Mock
    private GodApiClient godApiClient;

    private GodService godService;

    @BeforeEach
    void setUp() {
        godService = new GodService(godApiClient);
    }

    @Test
    @DisplayName("Should return expected sum matching real API data")
    void should_returnExpectedSum_when_apiDataMatchesRealApis() {
        // Given - mirroring the actual API data from the three external services
        when(godApiClient.getGreekGods()).thenReturn(List.of(
            "Zeus", "Hera", "Poseidon", "Demeter", "Ares", "Athena",
            "Apollo", "Artemis", "Hephaestus", "Aphrodite", "Hermes",
            "Dionysus", "Hades", "Hypnos", "Nike", "Janus", "Nemesis",
            "Iris", "Hecate", "Tyche"
        ));
        when(godApiClient.getRomanGods()).thenReturn(List.of(
            "Venus", "Mars", "Neptun", "Mercury", "Pluto", "Jupiter"
        ));
        when(godApiClient.getNordicGods()).thenReturn(List.of(
            "Baldur", "Freyja", "Heimdall", "Frigga", "Hel", "Loki",
            "Njord", "Odin", "Thor", "Tyr"
        ));

        // When
        BigInteger result = godService.computeSum();

        // Then - Nike(78105107101) + Nemesis(78101109101115105115) + Neptun(78101112116117110) + Njord(78106111114100)
        assertThat(result).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    @DisplayName("Should return zero when no gods start with n in any API")
    void should_returnZero_when_noGodsStartWithN() {
        // Given
        when(godApiClient.getGreekGods()).thenReturn(List.of("Zeus", "Hera", "Apollo"));
        when(godApiClient.getRomanGods()).thenReturn(List.of("Venus", "Mars", "Jupiter"));
        when(godApiClient.getNordicGods()).thenReturn(List.of("Odin", "Thor", "Loki"));

        // When
        BigInteger result = godService.computeSum();

        // Then
        assertThat(result).isEqualTo(BigInteger.ZERO);
    }

    @Test
    @DisplayName("Should return zero when all APIs return empty lists")
    void should_returnZero_when_allApisReturnEmptyLists() {
        // Given
        when(godApiClient.getGreekGods()).thenReturn(List.of());
        when(godApiClient.getRomanGods()).thenReturn(List.of());
        when(godApiClient.getNordicGods()).thenReturn(List.of());

        // When
        BigInteger result = godService.computeSum();

        // Then
        assertThat(result).isEqualTo(BigInteger.ZERO);
    }

    @Test
    @DisplayName("Should calculate sum from a single matching god when others return empty lists")
    void should_calculateSum_when_onlyOneApiHasMatchingGods() {
        // Given - only Nordic API returns a god starting with 'n'
        when(godApiClient.getGreekGods()).thenReturn(List.of("Zeus", "Hera"));
        when(godApiClient.getRomanGods()).thenReturn(List.of());
        when(godApiClient.getNordicGods()).thenReturn(List.of("Njord", "Odin"));

        // When
        BigInteger result = godService.computeSum();

        // Then - Njord: N(78)j(106)o(111)r(114)d(100) -> "78106111114100"
        assertThat(result).isEqualTo(new BigInteger("78106111114100"));
    }

    @Test
    @DisplayName("Should sum gods across all three APIs when each contributes a matching god")
    void should_sumGodsAcrossAllApis_when_eachApiHasOneMatchingGod() {
        // Given
        when(godApiClient.getGreekGods()).thenReturn(List.of("Nike"));
        when(godApiClient.getRomanGods()).thenReturn(List.of("Neptun"));
        when(godApiClient.getNordicGods()).thenReturn(List.of("Njord"));

        // When
        BigInteger result = godService.computeSum();

        // Then - Nike(78105107101) + Neptun(78101112116117110) + Njord(78106111114100)
        BigInteger nike   = new BigInteger("78105107101");
        BigInteger neptun = new BigInteger("78101112116117110");
        BigInteger njord  = new BigInteger("78106111114100");
        assertThat(result).isEqualTo(nike.add(neptun).add(njord));
    }
}
