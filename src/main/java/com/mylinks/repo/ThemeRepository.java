package com.mylinks.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Theme;

import jakarta.transaction.Transactional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Theme t SET t.paid = :paid WHERE t.id = :id")
    void updatePaidStatus(@Param("id") UUID id,
                          @Param("paid") boolean paid);
}
