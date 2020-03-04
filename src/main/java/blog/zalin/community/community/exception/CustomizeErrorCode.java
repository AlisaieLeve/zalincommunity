package blog.zalin.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你要找的问题不见了，咋整呀，凑弟弟？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登陆，请先登录"),
    SYS_ERROR(2004,"系统错误"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"未找到评论"),
    CONTENT_IS_EMPTY(2007,"评论内容不能为空")
    ;

    private String message;
    private Integer code;


    CustomizeErrorCode(String message) {
        this.message = message;
    }
    CustomizeErrorCode(Integer code) {
        this.code = code;
    }
    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code=code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode(){
        return code;
    }
}

