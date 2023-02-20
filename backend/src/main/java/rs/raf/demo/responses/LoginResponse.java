package rs.raf.demo.responses;

import lombok.Data;
import rs.raf.demo.dto.UserDto;
import rs.raf.demo.model.User;

@Data
public class LoginResponse {
    private String jwt;
    private UserDto user;

    public LoginResponse(String jwt, UserDto user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
