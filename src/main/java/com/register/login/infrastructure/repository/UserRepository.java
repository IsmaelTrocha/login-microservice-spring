package com.register.login.infrastructure.repository;

import com.register.login.domain.entites.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  @Modifying
  @Transactional
  @Query(value = "UPDATE User c SET c.thumbnail = ?2 WHERE c.id= ?1")
  void updateCustomerProfileImageId(Long customerId, String imageId);

}
