package com.naver.bbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

// 자바코드로 테이블 생성.
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Article {
    @Id                     // table column PK
    @GeneratedValue                // id 값이 1씩 자동으로 증가.
    private Long id;

    @Column                 // table column
    private String title;

    @Column                 // table column
    private String content;

} // class end
