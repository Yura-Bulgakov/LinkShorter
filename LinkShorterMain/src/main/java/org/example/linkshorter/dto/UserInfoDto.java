package org.example.linkshorter.dto;

public class UserInfoDto {
    private Long userId;
    private String username;
    private String email;
    private long tokenCounts;
    private long redirectCounts;
    private boolean banned;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTokenCounts() {
        return tokenCounts;
    }

    public void setTokenCounts(long tokenCounts) {
        this.tokenCounts = tokenCounts;
    }

    public long getRedirectCounts() {
        return redirectCounts;
    }

    public void setRedirectCounts(long redirectCounts) {
        this.redirectCounts = redirectCounts;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", tokenCounts=" + tokenCounts +
                ", redirectCounts=" + redirectCounts +
                ", banned=" + banned +
                '}';
    }
}
