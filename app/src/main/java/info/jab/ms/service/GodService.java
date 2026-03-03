package info.jab.ms.service;

import info.jab.ms.client.GodApiClient;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class GodService {

    private final GodApiClient godApiClient;

    public GodService(GodApiClient godApiClient) {
        this.godApiClient = godApiClient;
    }

    public BigInteger computeSum() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var greekFuture = CompletableFuture
                .supplyAsync(godApiClient::getGreekGods, executor)
                .orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(e -> List.of());
            var romanFuture = CompletableFuture
                .supplyAsync(godApiClient::getRomanGods, executor)
                .orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(e -> List.of());
            var nordicFuture = CompletableFuture
                .supplyAsync(godApiClient::getNordicGods, executor)
                .orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(e -> List.of());

            return Stream.of(greekFuture, romanFuture, nordicFuture)
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith("n"))
                .map(this::toDecimal)
                .reduce(BigInteger.ZERO, BigInteger::add);
        }
    }

    private BigInteger toDecimal(String name) {
        var sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            sb.append((int) name.charAt(i));
        }
        return new BigInteger(sb.toString());
    }
}
