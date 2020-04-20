package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.UserDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.clint.id}")
    private String clientId;

    @Value("${github.clint.secret}")
    private String clientSecret;

    @Value("${login.callback.address}")
    private String callBackAddress;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(callBackAddress);
        accessTokenDTO.setState(state);
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        UserDTO user = gitHubProvider.getUser(token);
        if (user!=null){
            //登录成功，写session和cookie
            User userModel = new User();
            userModel.setToken(UUID.randomUUID().toString());
            userModel.setAccountId(String.valueOf(user.getId()));
            userModel.setName(user.getName());
            userModel.setGmtCreate(System.currentTimeMillis());
            userModel.setGmtMotified(userModel.getGmtCreate());
            userMapper.insert(userModel);
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
