package org.xij.web.module;

import org.springframework.web.bind.annotation.*;
import org.xij.util.Codec;
import org.xij.util.Result;
import org.xij.util.StringManager;
import org.xij.web.core.AuthService;
import org.xij.web.core.Caches;
import org.xij.web.util.Webs;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年11月19日 下午9:44:55
 */
@RestController
@RequestMapping(value = "", method = RequestMethod.POST)
public class HomeController {
    private AuthService authService;

    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "key")
    public Result getPublicKey() {
        KeyPair keyPair = Codec.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        Caches.set(publicKey, keyPair.getPrivate().getEncoded(), 15);
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey);
        return Result.ok(publicKeyBase64);
    }

    @RequestMapping(value = "refresh/token")
    public Result refreshToken() {
        String token = authService.refreshToken();
        return Result.ok(token);
    }

    @RequestMapping(value = "login")
    public Result login(String auth, String key) {
        byte[] publicKey = Base64.getDecoder().decode(key);
        byte[] privateKeyData = Caches.get(publicKey);

        if (null == privateKeyData) {
            return Result.error(StringManager.getManager(HomeController.class).getString("login.keyError"));
        }
        Caches.del(publicKey);

        byte[] decoded = Base64.getDecoder().decode(auth);
        PrivateKey privateKey = Codec.getPrivateKey(privateKeyData);
        byte[] data = Codec.decrypt(decoded, privateKey);

        String[] userInfo = new String(data).split("&");
        return authService.login(userInfo[0], userInfo[1], Webs.getIpAddr());
    }
}