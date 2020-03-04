package blog.zalin.community.community.service;

import blog.zalin.community.community.Mapper.QuestionExtMapper;
import blog.zalin.community.community.Mapper.QuestionMapper;
import blog.zalin.community.community.Mapper.UserMapper;
import blog.zalin.community.community.dto.PaginationDTO;
import blog.zalin.community.community.dto.QuestionDto;
import blog.zalin.community.community.exception.CustomizeErrorCode;
import blog.zalin.community.community.exception.CustomizeException;
import blog.zalin.community.community.model.Question;
import blog.zalin.community.community.model.QuestionExample;
import blog.zalin.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            offset = 0;
        }
        if (page > totalPage) {
            offset = size * (totalPage - 1);
        }

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper
                .selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDto> questionDtoList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDTO.setQuestions(questionDtoList);
        paginationDTO.setPagnation(totalCount, page, size);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            offset = 0;
        }
        if (page > totalPage) {
            offset = size * (totalPage - 1);
        }
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper
                .selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDto> questionDtoList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDTO.setQuestions(questionDtoList);
        paginationDTO.setPagnation(totalCount, page, size);

        return paginationDTO;
    }

    public QuestionDto getById(Long id) {


        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();

        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;

    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //insert
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            //update
            question.setGmtModified(System.currentTimeMillis());
            Question updataQuestion = new Question();
            updataQuestion.setGmtModified(System.currentTimeMillis());
            updataQuestion.setTag(question.getTag());
            updataQuestion.setTitle(question.getTitle());
            updataQuestion.setDescription(question.getDescription());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());


            int updated = questionMapper.updateByExampleSelective(updataQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);
    }
}
