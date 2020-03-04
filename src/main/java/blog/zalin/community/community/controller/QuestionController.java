package blog.zalin.community.community.controller;

import blog.zalin.community.community.dto.CommentDTO;
import blog.zalin.community.community.dto.QuestionDto;
import blog.zalin.community.community.enums.CommentTypeEnum;
import blog.zalin.community.community.service.CommentService;
import blog.zalin.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDto questionDto = questionService.getById(id);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        
        //
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";

    }
}
