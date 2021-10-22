package com.auron.controller;

import com.auron.model.ShortUrl;
import com.auron.repository.ShortRepository;

import com.auron.utils.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.time.ZonedDateTime;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class ShortController {

    @Value("${shorturl.length}")
    private Integer shorterLength;

    @Autowired
    private ShortRepository shortRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @PostMapping(path = "/", consumes = APPLICATION_JSON_VALUE)
    public ShortUrl createShortUrl(@RequestBody ShortUrl shortUrl){

        String hash = codeGenerator.generate(shorterLength);

        if(shortUrl != null){

            String shorterString = URLDecoder.decode(shortUrl.getOriginalUrl());

            shortUrl = new ShortUrl(null, hash, shorterString, ZonedDateTime.now());

            return shortRepository.save(shortUrl);
        }else{
            return null;
        }
    }

    @GetMapping(path = "/{hash}")
    public ResponseEntity redirectShortUrl(@PathVariable("hash") String hash){

        ShortUrl shortUrl = shortRepository.findByHash(hash);

        if(shortUrl != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", shortUrl.getOriginalUrl());

            return new ResponseEntity<String>(headers,HttpStatus.FOUND);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
