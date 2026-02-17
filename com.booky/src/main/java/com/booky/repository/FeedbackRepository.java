package com.booky.repository;

import com.booky.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM feedback f WHERE LOWER(f.text) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Feedback> findByKeyword(String keyword, Pageable pageable);
}
