package rs.raf.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.dto.UserCreateDto;
import rs.raf.demo.services.UserService;
import rs.raf.demo.services.UserServiceImpl;


@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() throws Exception {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws Exception{
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) throws Exception{
        try {
            return new ResponseEntity<>(userService.createUser(userCreateDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserCreateDto userCreateDto) throws Exception{
        return new ResponseEntity<>(userService.updateUser(id, userCreateDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws Exception{
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }


}
