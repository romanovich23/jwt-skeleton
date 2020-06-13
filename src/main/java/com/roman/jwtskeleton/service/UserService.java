package com.roman.jwtskeleton.service;

import com.roman.jwtskeleton.dto.UserDTO;
import com.roman.jwtskeleton.error.NullParametersException;
import com.roman.jwtskeleton.error.UserAlreadyExistsException;
import com.roman.jwtskeleton.model.entity.User;
import com.roman.jwtskeleton.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final Logger logger;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(Logger logger, ModelMapper modelMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.logger = logger;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * User save method
     *
     * @param userDTO User
     * @throws Exception When a JPA error occurs
     */
    public void saveUser(UserDTO userDTO) throws Exception {
        try {
            if (null == userDTO.getUsername() || null == userDTO.getPassword())
                throw new NullParametersException("Invalid parameters");
            User userFound = this.userRepository.findByUsername(userDTO.getUsername());
            if (null != userFound) throw new UserAlreadyExistsException(userDTO.getUsername());
            User user = this.modelMapper.map(userDTO, User.class);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        } catch (JpaSystemException e) {
            this.logger.error(e.getMessage());
            throw new Exception("Internal error");
        }
    }

    /**
     * User delete method
     *
     * @param username username
     * @throws Exception When a JPA error occurs
     */
    public void deleteUser(String username) throws Exception {
        try {
            if (null == username) throw new NullParametersException("Username is null");
            User user = this.userRepository.findByUsername(username);
            if (null == user) throw new UserAlreadyExistsException(username);
            this.userRepository.delete(user);
        } catch (JpaSystemException e) {
            this.logger.error(e.getMessage());
            throw new Exception("Internal error");
        }
    }

}
