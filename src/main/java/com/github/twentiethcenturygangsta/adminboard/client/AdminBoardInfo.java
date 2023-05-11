package com.github.twentiethcenturygangsta.adminboard.client;

import lombok.Builder;

public class AdminBoardInfo {
    private String title;
    private String description;
    private String license;
    private String licenseUrl;
    private String version;

    @Builder
    public AdminBoardInfo(String title, String description, String license, String licenseUrl, String version) {
        this.title = title;
        this.description = description;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.version = version;
    }
}
