package cn.tedu.csmall.product;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExceptionTests {

    void serviceMethod() throws IOException {
        throw new IOException();
    }

    void controllerMethod() throws IOException {
        serviceMethod();
    }

    // -----------------------------

    void a() {
        throw new RuntimeException();
    }

    void b() {
        try {
            a();
        } catch (RuntimeException e) {
        }
    }

    void c() {
        b();
    }

    @Test
    void test() {
        c();
    }

}
