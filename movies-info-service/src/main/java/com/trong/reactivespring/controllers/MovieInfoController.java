package com.trong.reactivespring.controllers;


import com.trong.reactivespring.models.MovieInfo;
import com.trong.reactivespring.repository.MovieInfoRepository;
import com.trong.reactivespring.services.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    /* ====================================================================================================
     |                                                    CREATE                                          |
     ===================================================================================================== */
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo){
        return movieInfoService.addMovieInfo(movieInfo);
    }


    /* ====================================================================================================
     |                                                    READ                                            |
     ===================================================================================================== */
    @GetMapping("/movies")
    public Flux<MovieInfo> getAllMovies(@RequestParam(value = "year", required = false) Integer year){
        if(year != null){
            return movieInfoService.findMovieByYear(year);
        }
        return movieInfoService.getAllMovieInfo();
    }


    @GetMapping("/movies/{id}")
    public Mono<MovieInfo> getMovieById(@PathVariable String id){
        return movieInfoService.getMovieById(id);
    }


    /* ====================================================================================================
     |                                                    UPDATE                                           |
     ===================================================================================================== */

    @PutMapping("/movies/{id}")
    public Mono<MovieInfo> updateMovies(@RequestBody MovieInfo movieInfo, @PathVariable String id){
        return movieInfoService.updateMovie(movieInfo, id);
    }

    /* ====================================================================================================
     |                                                    DELETE                                           |
     ===================================================================================================== */
    @DeleteMapping("/movies/{id}")
    public Mono<Void> deleteMovies(@PathVariable String id){
        return movieInfoService.deleteMovie(id);
    }


}
