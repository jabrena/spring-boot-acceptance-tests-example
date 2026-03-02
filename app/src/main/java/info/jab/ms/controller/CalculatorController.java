package info.jab.ms.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    public record SumRequest(@NotNull Integer a, @NotNull Integer b) {}

    @PostMapping("/sum")
    public Integer sum(@Valid @RequestBody SumRequest request) {
        return request.a() + request.b();
    }
}
