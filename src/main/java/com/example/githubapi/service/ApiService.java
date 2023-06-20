package com.example.githubapi.service;

import com.example.githubapi.pojo.Branch;
import com.example.githubapi.pojo.Repository;
import com.example.githubapi.exeptions.ResourceNotFoundException;
import com.example.githubapi.payload.RepositoryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    public RepositoryResponse getRepositories(String username){

        List<Repository> repositories = getAllNoForkRepositories(username);

        for(Repository repository : repositories){
            List<Branch> branches = getBranches(repository);
            repository.setBranches(branches);
        }

        RepositoryResponse response = new RepositoryResponse();
        response.setRepositories(repositories);

        return response;
    }

    private List<Repository> getAllNoForkRepositories(String username) {

        RestTemplate restTemplate = new RestTemplate();
        List<Repository> repositories = new ArrayList<>();

        String apiUrl = "https://api.github.com/users/" + username + "/repos";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                if (jsonNode.isArray()) {
                    for (JsonNode element : jsonNode) {
                        String repositoryName = element.get("name").asText();
                        String isFork = element.get("fork").asText();
                        String owner = element.get("owner").get("login").asText();

                        if (isFork.equals("false")) {
                            Repository repository = new Repository(repositoryName, owner);
                            repositories.add(repository);
                        }
                    }
                }
            } catch (JsonProcessingException exception) {
                throw new RuntimeException("Error processing JSON");
            }
        } catch (HttpClientErrorException.NotFound exception){
            throw new ResourceNotFoundException("User", "username", username);
        }
        return repositories;
    }

    private List<Branch> getBranches(Repository repository) {

        RestTemplate restTemplate = new RestTemplate();
        List<Branch> branches = new ArrayList<>();

        String apiUrl = "https://api.github.com/repos/" + repository.getOwnerLogin() + "/" + repository.getName() + "/branches";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                if (jsonNode.isArray()) {
                    for (JsonNode element : jsonNode) {
                        Branch branch = new Branch();
                        branch.setName(element.get("name").asText());
                        branch.setLastCommitSha(element.get("commit").get("sha").asText());
                        branches.add(branch);
                    }
                }
            } catch (JsonProcessingException exception) {
                throw new RuntimeException("Error processing JSON");
            }
        } catch (HttpClientErrorException.NotFound exception){
            throw new ResourceNotFoundException("Repository", "name", repository.getName());
        }
        return branches;
    }
}
