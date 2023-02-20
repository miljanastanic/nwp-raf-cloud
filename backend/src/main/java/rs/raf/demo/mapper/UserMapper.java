package rs.raf.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.raf.demo.dto.UserCreateDto;
import rs.raf.demo.dto.UserDto;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Permission;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.PermissionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    @Autowired
    PermissionRepository permissionRepository;

    public User createUser(UserCreateDto userCreateDto) throws Exception{
        User user = new User();
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        List<Permission> permissions = new ArrayList<>();
        List<Machine> machines = new ArrayList<>();
        for(String permissionString: userCreateDto.getPermissions()) {
            Permission permission = permissionRepository.findPermissionByValue(permissionString).orElseThrow(() -> new Exception(String.format("%s is not a valid permission.", permissionString)));
            permissions.add(permission);
        }
        user.setPermissions(permissions);
        return user;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        List<String> permissions = new ArrayList<>();
        for(Permission permission: user.getPermissions()) {
            permissions.add(permission.getValue());
        }
        userDto.setPermissions(permissions);
        return userDto;
    }
    public User updateUser(User user, UserCreateDto updatedUser) throws Exception{
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        List<Permission> permissions = new ArrayList<>();
        for(String permissionString: updatedUser.getPermissions()) {
            Permission permission = permissionRepository.findPermissionByValue(permissionString).orElseThrow(() -> new Exception(String.format("%s is not a valid permission.", permissionString)));
            permissions.add(permission);
        }
        user.setPermissions(permissions);
        return user;
    }
}
