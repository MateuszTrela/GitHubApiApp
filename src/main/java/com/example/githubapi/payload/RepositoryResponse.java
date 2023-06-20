package com.example.githubapi.payload;

import com.example.githubapi.pojo.Repository;

import java.util.List;

public class RepositoryResponse {
    List<Repository> repositories;

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
