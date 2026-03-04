package info.jab.ms.controller;

import info.jab.ms.service.CalculatorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    private final @NonNull CalculatorService calculatorService;

    public CalculatorController(@NonNull CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public record SumRequest(@NotNull @NonNull Integer a, @NotNull @NonNull Integer b) {}

    @PostMapping("/sum")
    public @NonNull Integer sum(@Valid @RequestBody @NonNull SumRequest request) {
        return calculatorService.sum(request.a(), request.b());
    }
}
