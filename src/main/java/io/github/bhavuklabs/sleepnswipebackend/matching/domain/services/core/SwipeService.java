package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import org.springframework.transaction.annotation.Transactional;

public interface SwipeService {

    @Transactional
    void processSwipe(SwipeRequestDomain requestDomain) throws Exception;

}
