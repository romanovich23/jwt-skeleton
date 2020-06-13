package com.roman.jwtskeleton.service;

import com.roman.jwtskeleton.error.NullParametersException;
import com.roman.jwtskeleton.error.UserAlreadyExistsException;
import com.roman.jwtskeleton.model.entity.User;
import com.roman.jwtskeleton.model.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final Logger logger;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(Logger logger, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * User save method
     *
     * @param user User
     * @throws Exception When a JPA error occurs
     */
    public void saveUser(User user) throws Exception {
        try {
            if (null == user.getUsername() || null == user.getPassword())
                throw new NullParametersException("Invalid parameters");
            User userFound = this.userRepository.findByUsername(user.getUsername());
            if (null != userFound) throw new UserAlreadyExistsException(user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        } catch (JpaSystemException e) {
            this.logger.error(e.getMessage());
            throw new Exception("Internal error");
        }
    }

}
