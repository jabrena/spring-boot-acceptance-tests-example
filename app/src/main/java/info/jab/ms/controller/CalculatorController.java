package info.jab.ms.controller;

import info.jab.ms.service.CalculatorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@NullMarked
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public record SumRequest(@NotNull Integer a, @NotNull Integer b) {}

    @PostMapping("/sum")
    public Integer sum(@Valid @RequestBody SumRequest request) {
        return calculatorService.sum(request.a(), request.b());
    }
}
