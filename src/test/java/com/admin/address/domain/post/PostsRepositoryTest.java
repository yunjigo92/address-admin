package com.admin.address.domain.post;

import com.admin.address.domin.post.Posts;
import com.admin.address.domin.post.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

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
}
