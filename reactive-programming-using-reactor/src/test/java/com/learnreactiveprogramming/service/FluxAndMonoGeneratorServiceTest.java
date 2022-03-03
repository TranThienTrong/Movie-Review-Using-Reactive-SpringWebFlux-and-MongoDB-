package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    Client client = new Client();

    @Test
    void fluxMergeDatasource() {

        //given

        //when
        var value = client.fluxMergeDatasource();

        //then
        StepVerifier.create(value)
                .expectNext("tran", "trong", "thien")
                .verifyComplete();

    }
    @Test
    void fluxMergeSequentialDatasource() {

        //given

        //when
        var value = client.fluxMergeSequentialDatasource();

        //then
        StepVerifier.create(value)
                .expectNext("tran", "thien", "trong")
                .verifyComplete();

    }
}