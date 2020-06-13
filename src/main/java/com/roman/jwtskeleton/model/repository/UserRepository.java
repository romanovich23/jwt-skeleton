package com.roman.jwtskeleton.model.repository;

import com.roman.jwtskeleton.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
