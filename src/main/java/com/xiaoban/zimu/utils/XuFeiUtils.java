package com.xiaoban.zimu.utils;

import com.alibaba.fastjson.JSON;
import com.xiaoban.zimu.model.SFReuslt;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoban
 * @create 2021/03/04 - 19:28
 */
@Slf4j
public class XuFeiUtils {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.16; rv:86.0) Gecko/20100101 Firefox/86.0";

    private static  final String VPS_URL = "https://api.sanfengyun.com/www/vps.php";
    private static final int SUCCESS_CODE = 200;

    private static final String CMD_LOGIN =	"login";
    private static final String CMD_VPS_LIST =	"login";
    private static final String ID_MOBILE ="17631176046";
    private static final String PASSWORD ="jinhua520";

    public static void list(CloseableHttpClient httpClient,Header[] cookies1 ) throws IOException {
        //创建HttpClient客户端
        HttpPost httpPost = new HttpPost(VPS_URL);
        httpPost.setHeader("Connection","keep-alive");
        // 设置请求参数
        List<NameValuePair> parameters = new ArrayList<>(0);
        parameters.add(new BasicNameValuePair("cmd", CMD_VPS_LIST));
        parameters.add(new BasicNameValuePair("vps_type", "free"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        httpPost.setHeader("User-Agent",USER_AGENT);
        httpPost.setHeaders(cookies1);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //相应结果
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode!=SUCCESS_CODE){
            log.error("请求失败了");
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        SFReuslt sf = JSON.parseObject(result, SFReuslt.class);
        if(sf==null||!sf.ok()){
            log.error("请求失败了");
        }else {
            log.info("请求结果 {}",UnicodeUtil.unicodeToString(sf.getMsg().toString()));
        }
        response.close();
        httpClient.close();
    }

    public static void main(String[] args) throws IOException {
        login();
    }

    private static void login() throws IOException {
        String uri = "https://api.sanfengyun.com/www/login.php";

        //创建HttpClient客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求方式  post  get  http://localhost:8888/demo/test/

        //String uri = "https://www.sanfengyun.com/control/#/freeServerList";
        HttpPost httpPost = new HttpPost(uri);

        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);

        parameters.add(new BasicNameValuePair("cmd", CMD_LOGIN));
        parameters.add(new BasicNameValuePair("id_mobile", ID_MOBILE));
        parameters.add(new BasicNameValuePair("password", PASSWORD));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=\"UTF-8\"");
        httpPost.setHeader("Connection","keep-alive");
        httpPost.setHeader("User-Agent",
                USER_AGENT);
        httpPost.addHeader(new BasicHeader("Cookie", ""));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //相应结果
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode!=SUCCESS_CODE){
            log.error("请求失败了");
        }

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity);
//        result = UnicodeUtil.unicodeToString(result);
        SFReuslt parse = JSON.parseObject(result, SFReuslt.class);
        log.info("请求结果 {}",parse);

        list(httpClient,response.getHeaders("Cookie"));

        response.close();




        httpClient.close();
    }
}
