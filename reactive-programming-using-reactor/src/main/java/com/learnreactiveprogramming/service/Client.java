package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class Client {


    public Flux<String> fluxDatasource() {
        return Flux.fromIterable(List.of("tran", "thien", "trong")).log();
    }

    public Mono<String> monoDatasource() {
        return Mono.just("trong").log();
    }

    public Flux<String> fluxMapDatasource(String letter) {
        return Flux.fromIterable(List.of("tran", "thien", "trong"))
                .map(element -> element.toUpperCase())
                .filter(element -> element.contains(letter.toUpperCase()))
                .log();
    }

    public Flux<String> fluxFlatMapDatasource() {
        return Flux.fromIterable(List.of("thien", "trong"))
                .flatMap(element ->
                        Flux.fromArray(element.split("")))
                .log();
    }


    public Flux<String> fluxFlatMaManyDatasource() {
        return Mono.just("trong")
                .flatMapMany(element -> Flux.fromArray(element.split("")))
                .log();
    }


    public Flux<String> fluxTransformDatasource() {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(e -> e.toUpperCase());

        return Flux.fromIterable(List.of("thien", "trong"))
                .transform(filterMap)
                .log();
    }

    public Flux<?> fluxEmptyDatasource() {
        var defaultFlux = Flux.fromIterable(List.of("thien"));

        return Flux.fromIterable(List.of())
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> fluxConcatDatasource() {
        var firstFlux = Flux.fromIterable(List.of("thien"));
        var secondFlux = Flux.fromIterable(List.of("trong"));

        return firstFlux.concatWith(secondFlux).log();
    }

    public Flux<String> fluxMergeDatasource() {
        var firstFlux = Flux.just("tran","thien").delayElements(Duration.ofMillis(100));
        var secondFlux = Flux.just("trong").delayElements(Duration.ofMillis(125));

        return Flux.merge(firstFlux,secondFlux).log();

        //return Flux.mergeSequential(firstFlux,secondFlux).log();
    }

    public Flux<String> fluxMergeSequentialDatasource() {
        var firstFlux = Flux.just("tran","thien").delayElements(Duration.ofMillis(100));
        var secondFlux = Flux.just("trong").delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(firstFlux,secondFlux).log();
    }

    public Flux<String> fluxZipDatasource() {
        var firstFlux = Flux.just("tran","trong");
        var secondFlux = Flux.just("thien", "cute");

        return Flux.zip(firstFlux,secondFlux, ( x, y)-> x+" "+y).log();
    }

    public Flux<String> fluxAdvanceZipDatasource() {
        var firstTupe = Flux.just("tran","cute");
        var secondTupe = Flux.just("thien", " ");
        var thirdTupe = Flux.just("trong", "phomaique");

        return Flux.zip(firstTupe,secondTupe,thirdTupe).map( tupe -> tupe.getT1() + tupe.getT2() + tupe.getT3()).log();
    }


    public static void main(String[] args) {
        Client client = new Client();

        client.fluxAdvanceZipDatasource().subscribe((result)->{
            System.out.println(result);
        });

//        client.fluxZipDatasource().subscribe((result)->{
//            System.out.println(result);
//        });

//        client.fluxMergeDatasource().subscribe(System.out::println);

//        client.fluxConcatDatasource().subscribe(System.out::println);

//        client.fluxEmptyDatasource().subscribe(result -> {
//            System.out.println(result);
//        });

//        client.fluxTransformDatasource().subscribe(result -> {
//            System.out.println(result);
//        });

//        client.fluxFlatMaManyDatasource().subscribe(result -> {
//            System.out.println(result);
//        });

//        client.fluxMapDatasource("r").subscribe(result -> {
//            System.out.println(result);
//        });

//        client.fluxFlatMapDatasource().subscribe(result -> {
//            System.out.println(result);
//        });

//        client.monoDatasource().subscribe( result -> {
//            System.out.println(result);
//        });
//

    }


}
