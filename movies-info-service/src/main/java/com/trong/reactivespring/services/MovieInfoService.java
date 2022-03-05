package com.trong.reactivespring.services;

import com.trong.reactivespring.models.MovieInfo;
import com.trong.reactivespring.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MovieInfoService {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo){
        return movieInfoRepository.save(movieInfo);
    }


    public Flux<MovieInfo> getAllMovieInfo(){
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> updateMovie(MovieInfo movieInfo, String id) {

        var foundMovie = movieInfoRepository.findById(id);

        return foundMovie.flatMap( movie -> {
            movie.setName(movieInfo.getName());
            movie.setCast(movieInfo.getCast());
            movie.setRelease_date(movieInfo.getRelease_date());
            movie.setYear(movieInfo.getYear());

            return movieInfoRepository.save(movie);
        });
    }

    public Mono<Void> deleteMovie(String id) {
        return movieInfoRepository.deleteById(id);
    }

    public Flux<MovieInfo> findMovieByYear(Integer year) {
        return movieInfoRepository.findMovieByYear(year);
    }

}
