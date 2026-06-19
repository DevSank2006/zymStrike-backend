package com.sanket.Zyvix.Repository;

import com.sanket.Zyvix.Entities.HealthRecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthRecordsRepository extends JpaRepository<HealthRecordsEntity,Long> {
    public HealthRecordsEntity findFirstByUserIdOrderByDateDescIdDesc(long id);

    HealthRecordsEntity findFirstByUserIdOrderByDateDesc(long id);
}
