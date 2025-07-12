package com.portfolio.myportfolio.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.myportfolio.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

  Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);
}