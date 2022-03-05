package com.trong.reactivespring.repository;

import com.trong.reactivespring.models.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {
    Flux<MovieInfo> findMovieByYear(Integer year);
}
