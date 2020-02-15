package blog.zalin.community.community.controller;

import blog.zalin.community.community.dto.AccessTokenDTO;
import blog.zalin.community.community.dto.GithubUser;
import blog.zalin.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("e4b0d78d737e38179b6d");
        accessTokenDTO.setClient_secret("00f2cae6fa8e993c0291fcb8a9bf0ba2527f826a");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println(accessToken);
        GithubUser user=githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
//        System.out.println(user.getId());
//        System.out.println(user.getBio());

        return "index";
    }
}
