package info.jab.ms.service;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public @NonNull Integer sum(int a, int b) {
        return a + b;
    }
}
