package com.spring_security.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByUserName(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.userName = :username")
Optional<User> findByUserName(@Param("username") String username);
}
