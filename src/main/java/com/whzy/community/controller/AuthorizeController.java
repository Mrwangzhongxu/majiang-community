package com.whzy.community.controller;

import com.whzy.community.domain.User;
import com.whzy.community.dto.AccessTokenDTO;
import com.whzy.community.dto.GithubUser;
import com.whzy.community.provider.GithubProvider;
import com.whzy.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.clientSecret}")
    private String clientSecret;
    @Value("${github.redirectUri}")
    private String redirectUri;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        log.info("accessToken :{}",accessToken);
        //获取用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null){
            //存在 将用户信息保存到session中 并重定向
            request.getSession().setAttribute("user",githubUser);
            //并将用户信息保存到数据库中
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getLogin());
            user.setAccessId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(System.currentTimeMillis());
            this.userService.insertUser(user);
        }
        return "redirect:/";
    }

}
