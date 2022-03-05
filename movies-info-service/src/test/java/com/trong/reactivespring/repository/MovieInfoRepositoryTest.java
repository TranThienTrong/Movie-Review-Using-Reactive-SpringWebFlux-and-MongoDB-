package com.trong.reactivespring.repository;

import com.trong.reactivespring.models.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@ActiveProfiles("test")
class MovieInfoRepositoryTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;


    @BeforeEach
    void setUp() {
        var moviesInfoList = List.of(
                new MovieInfo(null, "No way home", 2020, List.of("Peter Parker", "Zendaya"), LocalDate.parse("2020-06-15")),
                new MovieInfo("abc", "Far from home", 2021, List.of("Peter Parker", "Zendaya"), LocalDate.parse("2021-06-15")),
                new MovieInfo(null, "Homeless", 2021, List.of("Trong", "Thien"), LocalDate.parse("2022-06-15")));

        movieInfoRepository.saveAll(moviesInfoList).blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll();
    }


    @Test
    void findAll(){
        var movieInfoFlux = movieInfoRepository.findAll();
        StepVerifier.create(movieInfoFlux).expectNextCount(3).verifyComplete();
    }

    @Test
    void findById(){
        var movieInfoMono = movieInfoRepository.findById("abc").log();

        StepVerifier.create(movieInfoMono).assertNext((movie) -> {
            assertEquals("Far from home", movie.getName());
        }).verifyComplete();
    }

    @Test
    void save(){
        var movieInfoMono = movieInfoRepository.save( new MovieInfo(null, "Sell House", 2023, List.of("Trong", "Thien"), LocalDate.parse("2023-06-15"))).log();


        StepVerifier.create(movieInfoMono).assertNext((movie) -> {
            assertNotNull(movieInfoRepository, movie.getMovieInfoId());
        }).verifyComplete();
    }

    @Test
    void upload(){
        var movieInfoMono = movieInfoRepository.findById("abc").block();

        if(movieInfoMono !=null){
            movieInfoMono.setName("Very far from home");
            var updatedMovieInfoMono = movieInfoRepository.save(movieInfoMono).log();


            StepVerifier.create(updatedMovieInfoMono).assertNext((movie) -> {
                assertEquals("Very far from home", movie.getName());
            }).verifyComplete();
        }

    }

    @Test
    void delete(){
        movieInfoRepository.deleteById("abc").block();
        var movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(movieInfoFlux).expectNextCount(2).verifyComplete();
        }

    }







