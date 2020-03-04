package blog.zalin.community.community.provider;

import blog.zalin.community.community.dto.AccessTokenDTO;
import blog.zalin.community.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {


    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;

    public static OkHttpClient mOkHttpClient =
            new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                    .build();


    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");



//        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
//                .addHeader("","")
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = mOkHttpClient.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String s = split[0];
            String token = s.split("=")[1];
//            System.out.println(string);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;

    }

    public GithubUser getUser(String accessToken){
//        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token"+accessToken)
                .build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return  null;
    }
}

