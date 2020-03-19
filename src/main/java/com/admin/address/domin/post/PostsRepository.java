package com.admin.address.domin.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface PostsRepository extends JpaRepository<Posts,Long> {
}
