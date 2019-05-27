package org.xij.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.xij.util.Asserts;
import org.xij.util.Result;
import org.xij.web.core.AuthService;
//import org.xij.web.core.XIJInterceptor;
import org.xij.util.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=9990",
                "server.servlet.context-path=/test"
        })
public class WebApplicationTests {
//    @Value("${server.port}")
//    private int port;
//    @Value("${server.servlet.context-path}")
//    private String ctx;
//    private TestRestTemplate template = new TestRestTemplate();
//    @Autowired
//    private XIJInterceptor interceptor;
//    private String baseURL;
//
//    @Before
//    public void init() {
//        baseURL = "http://127.0.0.1:" + port + ctx;
//    }

    @Test
    public void contextLoads() {
//        String url = baseURL + "/auth/login";
//        System.out.println(url);
//        Result[] results = {Result.ok(true), Result.resp(Result.CODE_AUTH, null)};
//        for (Result result : results) {
//            interceptor.setAuthService(new AuthService() {
//                @Override
//                public Result login(String account, String password, String ip) {
//                    return null;
//                }
//
//                @Override
//                public Result check(String module, String func, HttpServletRequest request) {
//                    Asserts.isTrue("test.controller.Auth".equals(module), "module name detection failed");
//                    Asserts.isTrue("needLogin".equals(func), "module name detection failed");
//                    return result;
//                }
//
//                @Override
//                public Result parseToken(String token) {
//                    return null;
//                }
//            });
//            Map resp = template.postForObject(url, null, Map.class);
//            String str = JSON.toString(resp);
//            String resultStr = JSON.toString(result);
//            Asserts.isTrue(str.equals(resultStr), "result no matched");
//        }
    }
}
