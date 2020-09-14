package tacs.wololo.model.DTOs;

public class UserInfoDto {

    String username;
    String email;


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

    public UserInfoDto() {
    }

    public UserInfoDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
