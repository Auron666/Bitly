package com.auron.controller;

import com.auron.model.ShortUrl;
import com.auron.repository.ShortRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ShortController {

    @Autowired
    private ShortRepository shortRepository;

    @PostMapping(path = "/")
    public String createShortUrl(String original){
        return null;
    }

    @GetMapping(path = "/{hash}")
    public ResponseEntity redirectShortUrl(@PathVariable("hash") String hash){

        log.info("Hash: " + hash);

        ShortUrl shortUrl = shortRepository.findByHash(hash);

        if(shortUrl != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", shortUrl.getOriginalUrl());

            log.info("ShortUrl: " + shortUrl.getOriginalUrl());

            return new ResponseEntity<String>(headers,HttpStatus.FOUND);

        }else{

            return ResponseEntity.notFound().build();
        }
    }

}
