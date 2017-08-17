package com.tim_wro.skupstina.repository;

import com.tim_wro.skupstina.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents Authority repository
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

        Authority findByName(String name);
}
