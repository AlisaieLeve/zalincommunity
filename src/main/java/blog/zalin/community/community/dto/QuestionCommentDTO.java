package blog.zalin.community.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 包含一个评论以及此评论的评论
 */
@Data
public class QuestionCommentDTO {
    CommentDTO commentDTO;
    List<CommentDTO> commentDTOList;
}
