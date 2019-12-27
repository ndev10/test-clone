package com.oauth.repository;

import com.oauth.domain.User;
import com.oauth.dto.UserDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
   @Query("SELECT new com.oauth.dto.UserDetailsDTO(id,firstName,lastName,email,username,password) " +
           "FROM User u WHERE UPPER(u.username)= UPPER(:username) " )
   UserDetailsDTO findByUsernameAndOrganizationCode(@Param("username") String username);





}
