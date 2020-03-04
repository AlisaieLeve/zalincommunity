package blog.zalin.community.community.Mapper;

import blog.zalin.community.community.model.Question;

public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);

}