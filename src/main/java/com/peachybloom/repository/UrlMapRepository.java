package com.peachybloom.repository;

import com.peachybloom.domain.entity.UrlMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {

    UrlMap findByOriginUrl(String originUrl);
}
