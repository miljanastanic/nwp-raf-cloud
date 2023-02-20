package rs.raf.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.raf.demo.dto.UserCreateDto;
import rs.raf.demo.dto.UserDto;

import java.util.List;
@Service
public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers() throws Exception;
    UserDto getUser(Long id) throws Exception;
    UserDto getLoggedUser(String email) throws Exception;

    UserDto createUser(UserCreateDto createUserDto) throws Exception;
    UserDto updateUser(Long id, UserCreateDto updateUserDto) throws Exception;
    UserDto deleteUser(Long id) throws Exception;

    void checkPrivileges(String action) throws Exception;
}
