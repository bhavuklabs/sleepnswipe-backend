package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core;

import java.util.UUID;

public abstract class KafkaMatchProcessor {

    public abstract void processMatchCandidates(UUID uuid);
}
