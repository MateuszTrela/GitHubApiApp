package com.example.githubapi.pojo;

import java.util.List;

public class Repository {
    private String name;
    private String ownerLogin;
    private List<Branch> branches;

    public Repository(String name, String ownerLogin) {
        this.name = name;
        this.ownerLogin = ownerLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
