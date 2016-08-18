package com.bairesdev.entity;

/**
 * This class stores the information of a profile taken from the file as a DTO
 */
public class Profile {

    private String publicProfileURL;
    private String name;
    private String lastName;
    private String title;
    private String geographicArea;
    private int numberOfRecommendations;
    private int numberOfConnections;
    private String currentRole;
    private String industry;
    private String country;

    public String getPublicProfileURL() {
        return publicProfileURL;
    }

    public void setPublicProfileURL(String publicProfileURL) {
        this.publicProfileURL = publicProfileURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeographicArea() {
        return geographicArea;
    }

    public void setGeographicArea(String geographicArea) {
        this.geographicArea = geographicArea;
    }

    public int getNumberOfRecommendations() {
        return numberOfRecommendations;
    }

    public void setNumberOfRecommendations(int numberOfRecommendations) {
        this.numberOfRecommendations = numberOfRecommendations;
    }

    public int getNumberOfConnections() {
        return numberOfConnections;
    }

    public void setNumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
