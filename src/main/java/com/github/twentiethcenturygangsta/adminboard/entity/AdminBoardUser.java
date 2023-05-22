package com.github.twentiethcenturygangsta.adminboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminBoardUser {

    @Id
    @GeneratedValue
    @Column(name = "admin_board_user_id")
    private Long id;
    private String userId;
    private String password;

    private Boolean hasCreateObjectAuthority;
    private Boolean hasCreateAdminBoardUserAuthority;
    private Boolean hasUpdateObjectAuthority;
    private Boolean hasDeleteObjectAuthority;

    @Builder
    public AdminBoardUser(
            String userId,
            String password,
            Boolean hasCreateObjectAuthority,
            Boolean hasCreateAdminBoardUserAuthority,
            Boolean hasUpdateObjectAuthority,
            Boolean hasDeleteObjectAuthority
    ) {
        this.userId = userId;
        this.password = password;
        this.hasCreateObjectAuthority = hasCreateObjectAuthority;
        this.hasCreateAdminBoardUserAuthority = hasCreateAdminBoardUserAuthority;
        this.hasUpdateObjectAuthority = hasUpdateObjectAuthority;
        this.hasDeleteObjectAuthority = hasDeleteObjectAuthority;
    }

}
