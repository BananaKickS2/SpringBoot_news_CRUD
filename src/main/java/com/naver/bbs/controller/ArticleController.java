package com.naver.bbs.controller;

import com.naver.bbs.dto.ArticleForm;
import com.naver.bbs.entity.Article;
import com.naver.bbs.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
//    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);     // @Slf4j로 대체.
    @Autowired
    public ArticleRepository articleRepository;

    // 기사 전체목록
    @GetMapping("/articles")
    public String index(Model model) {
//        1. DB에서 모든 데이터 가져오기
        List<Article> articleList = (List<Article>) articleRepository.findAll();   // 다운캐스팅
//        2, 데이터를 Model에 등록(addAttribute)
        model.addAttribute("articleList", articleList);
//        3. View 설정.
        return "articles/index";            // index.mustache
    } // 기사 전체목록 메소드 end


    // 기사 추가에 대한 폼 화면 출력
    @GetMapping("/articles/new")         // 요청URL
    public String newArticleForm() {
        return "articles/new";           // View로 가도록.       new.mustache 파일
    }


    //    기사 추가
    @PostMapping("/article/create")       // new.mustache의 action.
    public String createArticle(ArticleForm form) {
        log.info(form.toString());                          // 폼에 입력된 데이터 확인.
//        1. DTO를 entity로 변환.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());                      // entity에 잘들어갔는지 확인.
//        2. repository로 entity를 DB에 저장.
        Article saved = articleRepository.save(articleEntity);    // insert하고 결과를 saved에 저장.
        log.info(saved.toString());                         // insert된 값 확인.
//        3. View 설정.
        return "redirect:/articles";
    } // 기사 추가 메소드 end


    // 기사 상세보기
        // a태그 URL : /articles/{1} => return DTO를 View에 출력.
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id 출력여부확인 : " + id);
//        1. DB에 id조회해서 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        log.info(articleEntity.toString());
//        2. Model에 데이터 추가하기
        model.addAttribute("article", articleEntity);
//        3. View로 return.
        return "articles/show";                 // 기사 1개 상세보기 화면. show.mustache
    } // 기사 상세보기 메소드 end


    // 수정화면에 대한 폼 화면 출력
        // <a href="/articles/{id}/edit">
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
//        1. DB에 id조회해서 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
//        2. Model에 데이터 추가하기
        model.addAttribute("article", articleEntity);
//        3. View로 return.
        return "articles/edit";       // edit.mustache
    }

    // 게시글 수정
    @PostMapping("/article/update")
    public String update(ArticleForm form) {
        log.info(form.toString());                          // 폼에 입력된 데이터 확인.
//        1. DTO를 entity로 변환.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
//        2. repository 메소드로 entity를 DB에 저장.(update)
//            2.1. id값으로 DB 안에 기존 데이터가 있는지 확인.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target != null) {
          articleRepository.save(articleEntity);
        }
//        3. View 설정.
        return "redirect:/articles/" + articleEntity.getId();
    } // 게시글 수정 메소드 end


    //      게시글 삭제
    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info(id + " 삭제요청 로그");
//        1. 해당 id로 DB에 삭제처리.
//            1.1 해당 id가 DB에 존재하는지 확인.(+해당 entity 생성해서 target에 넣어줌.)
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        }
//        2. View 설정.
        return "redirect:/articles";                     // 전체목록으로 redirect.
    }
/*
    redirect하면 "redirectL/articles?msg=정상적으로 삭제되었습니다." 같은 Query String 붙여주는게
    RedirectAttributes.
 */



} // class end
