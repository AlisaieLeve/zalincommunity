package blog.zalin.community.community.controller;

import blog.zalin.community.community.Mapper.CommentMapper;
import blog.zalin.community.community.dto.CommentCreateDTO;
import blog.zalin.community.community.dto.CommentDTO;
import blog.zalin.community.community.dto.ResultDTO;
import blog.zalin.community.community.enums.CommentTypeEnum;
import blog.zalin.community.community.exception.CustomizeErrorCode;
import blog.zalin.community.community.model.Comment;
import blog.zalin.community.community.model.User;
import blog.zalin.community.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        commentService.insert(comment);


        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id

    ) {
        List<CommentDTO> commentDTOs =commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return null;
    }
}
