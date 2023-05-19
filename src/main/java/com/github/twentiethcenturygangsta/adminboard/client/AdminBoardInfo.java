package com.github.twentiethcenturygangsta.adminboard.client;

import lombok.Builder;

public class AdminBoardInfo {
    private final String title;
    private final String description;
    private final String license;
    private final String licenseUrl;
    private final String version;

    @Builder
    public AdminBoardInfo(String title, String description, String license, String licenseUrl, String version) {
        this.title = title;
        this.description = description;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLicense() {return license;}
    public String getLicenseUrl() {return licenseUrl;}
    public String getVersion() {return version;}
}
