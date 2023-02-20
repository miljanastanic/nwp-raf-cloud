package rs.raf.demo.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.raf.demo.dto.UserCreateDto;
import rs.raf.demo.dto.UserDto;
import rs.raf.demo.mapper.UserMapper;
import rs.raf.demo.model.Permission;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = this.userRepository.findUserByUsername(username);
        if(myUser == null) {
            throw new UsernameNotFoundException("User name "+username+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(), new ArrayList<>());
    }

    @Override
    public List<UserDto> getAllUsers() throws Exception{
        checkPrivileges("can_read_users");
        List<UserDto> allUsersDto = new ArrayList<>();
        for(User user: userRepository.findAll()) {
            allUsersDto.add(userMapper.userToUserDto(user));
        }
        return allUsersDto;
    }
    @Override
    public UserDto getUser(Long id) throws Exception {
        checkPrivileges("can_read_users");
        User user = userRepository.findUserByUserId(id).orElseThrow(() -> new Exception(String.format("No user with id: %s found.", id)));
        return userMapper.userToUserDto(user);
    }
    @Override
    public UserDto createUser(UserCreateDto createUserDto) throws Exception {
        checkPrivileges("can_create_users");
        User newUser = userMapper.createUser(createUserDto);
        try {
            userRepository.save(newUser);
        }catch (Exception e) {
            throw new Exception("Email already in usage.");
        }
        return userMapper.userToUserDto(newUser);
    }
    @Override
    public UserDto updateUser(Long id, UserCreateDto updateUserDto) throws Exception{
        checkPrivileges("can_update_users");
        User user = userRepository.findUserByUserId(id).orElseThrow(() -> new Exception(String.format("No user with id: %s found.", id)));
        user = userMapper.updateUser(user, updateUserDto);
        try {
            userRepository.save(user);
        }catch (Exception e) {
            throw new Exception("Email already in usage.");
        }
        return userMapper.userToUserDto(user);
    }
    @Override
    public UserDto deleteUser(Long id) throws Exception {
        checkPrivileges("can_delete_users");
        User user = userRepository.findUserByUserId(id).orElseThrow(() -> new Exception(String.format("No user with id: %s found.", id)));
        UserDto userDto = userMapper.userToUserDto(user);
        userRepository.delete(user);
        return userDto;
    }
    @Override
    public UserDto getLoggedUser(String email) throws Exception{
        User user = userRepository.findUserByUsername(email);
        return userMapper.userToUserDto(user);
    }
    @Override
    public void checkPrivileges(String action) throws Exception {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        System.out.println(username);
        User loggedUser = userRepository.findUserByUsername(username);
        System.out.println(loggedUser);


        boolean permissionFound = false;

        for(Permission permission: loggedUser.getPermissions())

            if (permission.getValue().equals(action)) {
                permissionFound = true;
                break;
            }

        if(!permissionFound){
            throw new Exception("You don't have the permission");
        }
    }
}
