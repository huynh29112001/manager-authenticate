package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByStatus(Boolean status);

    Optional<User> findByUsername(String username);

    List<User> findByEmail(String email);

    @Query(value = "SELECT *\n" +
            "FROM user \n" +
            "JOIN user_auth ua on user.id = ua.id\n" +
            "JOIN authority on authority.auth_id = ua.auth_id\n" +
            "WHERE authority.name = ?1", nativeQuery = true)
    List<User> getUserByRole(String role);

    @Query(value = "SELECT *\n" +
            "FROM user\n" +
            "WHERE (id = ?1 OR ?1 IS NULL) AND (username = ?2 OR ?2 IS NULL) AND (status = ?3 OR ?3 IS NULL)", nativeQuery = true)
    List<User> getInfoNotRoleFromDB(Integer id, String userName, Boolean status);

    @Query(value = "SELECT * \n" +
            "from (SELECT *\n" +
            " FROM user \n" +
            " WHERE (id = ?1 OR ?1 IS NULL)  AND (username = ?2 OR ?2 IS NULL) AND (status = ?4 OR ?4 IS NULL)) u \n" +
            " JOIN user_auth on u.id = user_auth.id \n" +
            " JOIN authority on authority.auth_id = user_auth.auth_id\n" +
            " WHERE authority.name = ?3", nativeQuery = true)
    List<User> getInfoFromDB(Integer id, String userName, String role, Boolean status);


}
