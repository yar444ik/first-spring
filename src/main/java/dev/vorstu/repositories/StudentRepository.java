package dev.vorstu.repositories;

import dev.vorstu.entities.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query("from StudentEntity ")
    Page<StudentEntity> findAllByPageRequest(Pageable pageable);

    @Query("SELECT s FROM StudentEntity s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(s.surname) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(s.group) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<StudentEntity> findByFilter(@Param("filter") String filter, Pageable pageable);
}
