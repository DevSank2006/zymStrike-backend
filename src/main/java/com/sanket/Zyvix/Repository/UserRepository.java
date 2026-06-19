package com.sanket.Zyvix.Repository;
import com.sanket.Zyvix.Dto.UserReqDto;
import com.sanket.Zyvix.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    Optional<UserEntity> findByEmail(String email);
}
