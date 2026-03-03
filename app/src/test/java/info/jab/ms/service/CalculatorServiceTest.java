package info.jab.ms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Calculator Service Tests")
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("Should return correct sum for positive numbers")
    void should_returnCorrectSum_when_bothNumbersArePositive() {
        // Given
        int firstNumber = 5;
        int secondNumber = 10;
        int expectedSum = 15;

        // When
        Integer actualSum = calculatorService.sum(firstNumber, secondNumber);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Should return correct sum for negative numbers")
    void should_returnCorrectSum_when_bothNumbersAreNegative() {
        // Given
        int firstNumber = -5;
        int secondNumber = -10;
        int expectedSum = -15;

        // When
        Integer actualSum = calculatorService.sum(firstNumber, secondNumber);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Should return correct sum for mixed positive and negative numbers")
    void should_returnCorrectSum_when_numbersHaveDifferentSigns() {
        // Given
        int positiveNumber = 10;
        int negativeNumber = -3;
        int expectedSum = 7;

        // When
        Integer actualSum = calculatorService.sum(positiveNumber, negativeNumber);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Should return zero when both numbers are zero")
    void should_returnZero_when_bothNumbersAreZero() {
        // Given
        int firstNumber = 0;
        int secondNumber = 0;
        int expectedSum = 0;

        // When
        Integer actualSum = calculatorService.sum(firstNumber, secondNumber);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Should return the number itself when adding zero")
    void should_returnNumber_when_addingZero() {
        // Given
        int number = 42;
        int zero = 0;
        int expectedSum = 42;

        // When
        Integer actualSum = calculatorService.sum(number, zero);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @ParameterizedTest(name = "sum({0}, {1}) should equal {2}")
    @CsvSource({
        "1, 2, 3",
        "0, 0, 0",
        "-5, 5, 0",
        "10, -3, 7",
        "-10, -5, -15",
        "100, 200, 300",
        "1000000, 2000000, 3000000"
    })
    @DisplayName("Should calculate correct sums for various input combinations")
    void should_calculateCorrectSum_for_variousInputs(int a, int b, int expected) {
        // Given - inputs provided by @CsvSource

        // When
        Integer actualSum = calculatorService.sum(a, b);

        // Then
        assertThat(actualSum).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should handle maximum integer values correctly")
    void should_handleMaximumValues_when_withinIntegerRange() {
        // Given
        int maxValue = Integer.MAX_VALUE;
        int zero = 0;

        // When
        Integer actualSum = calculatorService.sum(maxValue, zero);

        // Then
        assertThat(actualSum).isEqualTo(maxValue);
    }

    @Test
    @DisplayName("Should handle minimum integer values correctly")
    void should_handleMinimumValues_when_withinIntegerRange() {
        // Given
        int minValue = Integer.MIN_VALUE;
        int zero = 0;

        // When
        Integer actualSum = calculatorService.sum(minValue, zero);

        // Then
        assertThat(actualSum).isEqualTo(minValue);
    }

    @Test
    @DisplayName("Should demonstrate commutative property of addition")
    void should_demonstrateCommutativeProperty_when_orderOfOperandsChanges() {
        // Given
        int firstNumber = 15;
        int secondNumber = 25;

        // When
        Integer sumAB = calculatorService.sum(firstNumber, secondNumber);
        Integer sumBA = calculatorService.sum(secondNumber, firstNumber);

        // Then
        assertThat(sumAB)
            .isEqualTo(sumBA)
            .isEqualTo(40);
    }

    @Test
    @DisplayName("Should handle edge case near integer overflow boundary")
    void should_handleEdgeCase_when_nearIntegerOverflowBoundary() {
        // Given
        int nearMaxValue = Integer.MAX_VALUE - 1;
        int one = 1;
        int expectedSum = Integer.MAX_VALUE;

        // When
        Integer actualSum = calculatorService.sum(nearMaxValue, one);

        // Then
        assertThat(actualSum).isEqualTo(expectedSum);
    }
}