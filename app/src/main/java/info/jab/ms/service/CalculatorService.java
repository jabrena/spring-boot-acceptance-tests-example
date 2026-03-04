package info.jab.ms.service;

import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

@Service
@NullMarked
public class CalculatorService {

    public Integer sum(int a, int b) {
        return a + b;
    }
}
