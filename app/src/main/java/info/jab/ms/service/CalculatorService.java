package info.jab.ms.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Integer sum(int a, int b) {
        return a + b;
    }
}
