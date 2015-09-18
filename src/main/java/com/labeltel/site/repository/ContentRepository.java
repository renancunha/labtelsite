package com.labeltel.site.repository;

import com.labeltel.site.domain.Content;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Content entity.
 */
public interface ContentRepository extends JpaRepository<Content,Long> {
    Content findByName(String name);
}
