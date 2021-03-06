package com.aws.rooney.web;

import com.aws.rooney.domain.article.Article;
import com.aws.rooney.domain.article.ArticleRepository;
import com.aws.rooney.web.dto.ArticleSaveRequestDto;
import com.aws.rooney.web.dto.ArticleUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ArticleApiControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArticleRepository articleRepository;


    @Test
    public void createArticle() throws Exception {

        // given
        String title = "title";
        String content = "content";
        ArticleSaveRequestDto requestDto = ArticleSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/article";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList.get(0).getTitle()).isEqualTo(title);
        assertThat(articleList.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void update() throws Exception{

        // given
        Article saveArticle = articleRepository.save(Article.builder()
                .title("title")
                .content("content")
                .author("author").build());

        Long updateArticleId = saveArticle.getId();
        String nextTitle = "title2";
        String nextContent = "content2";

        ArticleUpdateRequestDto requestDto = ArticleUpdateRequestDto.builder()
                .title(nextTitle)
                .content(nextContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/article/" + updateArticleId;

        HttpEntity<ArticleUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);


        // when
        ResponseEntity<Long> responseEntity = restTemplate.
                exchange(url, HttpMethod.PUT,
                requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList.get(0).getTitle()).isEqualTo(nextTitle);
        assertThat(articleList.get(0).getContent()).isEqualTo(nextContent);

    }
}
