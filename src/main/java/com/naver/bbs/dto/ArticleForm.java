package com.naver.bbs.dto;

import com.naver.bbs.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

// 넘어오는 폼데이터를 저장하는 클래스
@AllArgsConstructor                   // 생성자 코드 대신 사용하는 어노테이션.
@ToString                             // toString() 오버라이딩 대신 사용하는 어노테이션.
public class ArticleForm {
    private Long id;
    private String title;
    private String content;

//    dto를 entity로 변환하는 메소드
    public Article toEntity() {
        return new Article(this.id, this.title, this.content);
    }

} // class end
