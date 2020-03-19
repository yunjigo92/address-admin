package com.admin.address.web.dto;

import com.admin.address.domain.post.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;


    //responseDto는 entity의 일부만 사용하므로 entity를 받아서 셋팅해준다.
    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
