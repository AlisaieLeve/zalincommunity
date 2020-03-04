package blog.zalin.community.community.controller;

import blog.zalin.community.community.Mapper.UserMapper;
import blog.zalin.community.community.dto.PaginationDTO;
import blog.zalin.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size) {


        PaginationDTO paginationDTO = questionService.list(page, size);
//        System.out.println(paginationDTO);
//        List<QuestionDto> questions=paginationDTO.getQuestions();
        model.addAttribute("paginationDTO", paginationDTO);
//        model.addAttribute("questions",questions);

        return "Index";
    }
}
