package com.example.githubapi.controller;

import com.example.githubapi.payload.RepositoryResponse;
import com.example.githubapi.service.ApiService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<RepositoryResponse> getRepositoriesByUsername(
            @PathVariable("username") String username,
            @RequestHeader(HttpHeaders.ACCEPT) String requestHeader
    ) throws HttpMediaTypeNotAcceptableException {

        if(requestHeader.equals("application/json")) {
            RepositoryResponse response = apiService.getRepositories(username);
            return ResponseEntity.ok(response);
        } else {
            String message = "Accept header: " + requestHeader + " not acceptable";
            throw new HttpMediaTypeNotAcceptableException(message);
        }
    }
}
