/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment-content").val();
    CommentToTarget(questionId,1,content);
}

function CommentToTarget(targetId, type, content) {
    if (!content) {
        alert("不能回复空的内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=e4b0d78d737e38179b6d&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(commentId) {
    var id = e.getAttribute("data-id");
    var content = $("#comment-"+id).val();
    CommentToTarget(commentId,2,comtent);
}

/**
 * 展开二级回复
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var collapse = e.getAttribute("data-collapse");
    if (collapse != null) {
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        $.getJSON("/comment/"+id,function (data) {

            //展开
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        });


    }
}