package com.github.twentiethcenturygangsta.adminboard.client;

import com.github.twentiethcenturygangsta.adminboard.enums.Role;
import lombok.Builder;

public class UserCredentials {
    private final String memberId;
    private final String password;
    private final Role role;

    /**
     * Constructs a new UserDatabaseCredentials object, with the specified user database
     * endpoint, id and password.
     *
     * @param memberId
     *            The user login id.
     * @param password
     *            The user login password.
     *
     * @exception IllegalArgumentException when there is no value among encryptKey, memberId, password
     */
    @Builder
    public UserCredentials(String memberId, String password) {
        if (memberId == null) {
            throw new IllegalArgumentException("MemberId cannot be null.");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        this.memberId = memberId;
        this.password = password;
        this.role = Role.SUPER_ADMIN;
    }

    public String getMemberId() {
        return memberId;
    }
    public String getPassword() {return password;}

    public Role getRole() {
        return role;
    }
}
