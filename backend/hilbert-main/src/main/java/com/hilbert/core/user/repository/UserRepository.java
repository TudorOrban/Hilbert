package com.hilbert.core.user.repository;

import com.hilbert.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserSearchRepository {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findByIds(@Param("ids") List<Long> ids);

    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
