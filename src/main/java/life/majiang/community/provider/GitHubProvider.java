package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.UserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String url = "https://github.com/login/oauth/access_token";
        String jsonParam = JSON.toJSONString(accessTokenDTO);
        String token = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, jsonParam);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            if (result!=null&&!"".equals(result)) {
                token = result.split("&")[0].split("=")[1];
            }
        } catch (IOException e) {
        }

        return token;
    }

    public UserDTO getUser(String token){
        UserDTO user = null;
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+token)
                    .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            System.out.println(result);
            user = JSON.parseObject(result,UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  user;
    }

}
