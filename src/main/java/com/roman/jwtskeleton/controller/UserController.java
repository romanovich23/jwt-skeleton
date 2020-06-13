package com.roman.jwtskeleton.controller;

import com.roman.jwtskeleton.dto.UserDTO;
import com.roman.jwtskeleton.error.NullParametersException;
import com.roman.jwtskeleton.error.UserAlreadyExistsException;
import com.roman.jwtskeleton.model.entity.User;
import com.roman.jwtskeleton.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/test")
    public String test() {
        return "¡Hola mundo!";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> save(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Saved user";

        try {
            User user = this.modelMapper.map(userDTO, User.class);
            this.userService.saveUser(user);
        } catch (UserAlreadyExistsException | NullParametersException e) {
            status = HttpStatus.BAD_REQUEST;
            message = e.getMessage();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = e.getMessage();
        }

        response.put("status", status.value());
        response.put("message", status.getReasonPhrase() + " - " + message);

        return ResponseEntity.status(status).body(response);
    }

}
