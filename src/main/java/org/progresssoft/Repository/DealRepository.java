package org.progresssoft.Repository;

import org.progresssoft.Model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
}
