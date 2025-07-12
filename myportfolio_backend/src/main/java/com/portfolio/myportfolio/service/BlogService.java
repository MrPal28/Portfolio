package com.portfolio.myportfolio.service;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.portfolio.myportfolio.entity.Blog;



public interface BlogService {

  

    public Page<Blog> getAllBlogs(Pageable pageable);
    public Optional<Blog> getBlogById(Long id);

    public Blog createBlog(Blog blog);

    public Blog updateBlog(Long id, Blog blogDetails);

    public void deleteBlog(Long id);
}