package cn.tedu.csmall.product;

import org.junit.jupiter.api.Test;

public class ExceptionTests {

    void a() {
        throw new RuntimeException();
    }
    void b() {
        try { a(); } catch (RuntimeException e) {}
    }
    void c() {
        b();
    }

    @Test
    void test() {
        c();
    }

}
