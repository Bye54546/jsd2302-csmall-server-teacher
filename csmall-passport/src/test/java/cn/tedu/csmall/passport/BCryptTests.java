package cn.tedu.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptTests {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Test
    void encode() {
        String rawPassword = "123456";
        System.out.println("原文：" + rawPassword);

        long start = System.currentTimeMillis();
        // for (int i = 0; i < 12; i++) {
            // String encodedPassword =
                    passwordEncoder.encode(rawPassword);
            // System.out.println("密文：" + encodedPassword);
        // }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }

    // 原文：123456
    // 密文：$2a$10$YOW67gn1jGQsNd1lWFOktuxGEK3Ai4obSCo6m0o0zP3YA4iTm0QoS
    // 密文：$2a$10$AoGlKthb1ZKzTAng5ssX6OUwN8.tC9junqbYhtF0POkr.XdFuoEWy
    // 密文：$2a$10$wgBhSmnoFQ.LdvFCLd8lyOSsHuGVIpVYKW8.bW4yt2kBMYqG1G.5u
    // 密文：$2a$10$OIiWGSjFH02Vr9khLEQnG.s2rGowkotMV14TThAgJK8KQm.WQq6pm
    // 密文：$2a$10$DluGioTO7Zcc0hmwDz8Ld.4Uyp2hIIZ/PcGhFCVd1P3FuSukqJN36

    @Test
    void matches() {
        String rawPassword = "123456";
        System.out.println("原文：" + rawPassword);

        String encodedPassword = "$2a$10$wgBhSmnoFQ.LdvFCLd8lyOSsHuGVIpVYKW8.bW4yt2kBMYqG1G.5u";
        System.out.println("密文：" + encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("匹配结果：" + result);
    }

}
