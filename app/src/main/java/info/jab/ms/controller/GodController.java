package info.jab.ms.controller;

import info.jab.ms.service.GodService;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GodController {

    private final @NonNull GodService godService;

    public GodController(@NonNull GodService godService) {
        this.godService = godService;
    }

    @GetMapping("/gods/sum")
    public @NonNull String getGodSum() {
        return godService.computeSum().toString();
    }
}
