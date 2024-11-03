package vstu.isd.urlshortenerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vstu.isd.urlshortenerservice.entity.Hash;
import vstu.isd.urlshortenerservice.generator.HashGenerator;
import vstu.isd.urlshortenerservice.repository.HashRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class HashService {

    private final HashGenerator hashGenerator;
    private final HashRepository hashRepository;
    private final int HASH_POOL_SIZE;
    private final int EXHAUSTION_POOL_PERCENTAGE;

    @Async("hashGeneratorThreadPool")
    @Transactional
    public CompletableFuture<List<String>> getHashesAsync(int amount) {
        return CompletableFuture.completedFuture(getHashes(amount));
    }

    @Transactional
    public List<String> getHashes(int amount) {

        if (amount > HASH_POOL_SIZE) {
            // TODO mb invoke directly hashGenerator
            throw new IllegalArgumentException("amount can't be greater than the hash-pool size: " + HASH_POOL_SIZE);
        }

        List<Hash> hashes = hashRepository.popAll(amount);

        if (hashes.size() < amount) {
            fillHashPoolIfNecessary();

            int lackingAmount = amount - hashes.size();
            hashes.addAll(hashRepository.popAll(lackingAmount));
        } else {
            fillHashPoolIfNecessaryAsync();
        }

        return hashes.stream()
                .map(Hash::getHashId)
                .toList();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRES_NEW
    )
    public void fillHashPoolIfNecessary() {
        int actualHashesCount = (int) hashRepository.count();

        if (isHashPoolExhausted(actualHashesCount)) {
            int lacking = HASH_POOL_SIZE - actualHashesCount;
            generateAndSaveHashes(lacking);
        }
    }

    private boolean isHashPoolExhausted(int actualHashesCount) {
        return actualHashesCount <= HASH_POOL_SIZE * EXHAUSTION_POOL_PERCENTAGE;
    }

    private void generateAndSaveHashes(int amount) {
        List<Hash> urlHashes = hashGenerator.generateHashes(amount)
                .map(Hash::new)
                .toList();

        hashRepository.saveAll(urlHashes);
    }

    @Async("hashGeneratorThreadPool")
    @Transactional(propagation = Propagation.SUPPORTS)
    public void fillHashPoolIfNecessaryAsync() {
        fillHashPoolIfNecessary();
    }
}
