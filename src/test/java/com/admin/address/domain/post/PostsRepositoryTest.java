package com.admin.address.domain.post;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {


    @Autowired
    PostsRepository postsRepository;


    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }
    @Test
    public void readContent(){
        //given
        String title ="게시글";
        String content = "테스트본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yunjigo92@gmail.com")
                .build());

        //When
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle().equals(title));
        assertThat(posts.getContent().equals(content));


    }

    @Test
    public void BaseTimeEntityTest(){
        //시간이 자동으로 잘 등록되느냥!!

        //given
        LocalDateTime now = LocalDateTime.of(2020,1,9,0,0,0);
        postsRepository.save(Posts.builder()
        .title("제목이당")
        .content("내용이당")
        .author("윤지")
        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts= postsList.get(0);

        System.out.println("생성시간~~~" + posts.getCreateDate()+"수정시간~~~"+posts.getModifiedDate());

        assertThat(posts.getCreateDate().isAfter(now));

        assertThat(posts.getModifiedDate().isAfter(now));



    }
}
