package cn.tedu.csmall.product.web;

public enum ServiceCode {

    OK(1), // new ServiceCode(1) // getValue() >> 1
    ERR_BAD_REQUEST(2); // new ServiceCode(2) // getValue() >> 2

    private Integer value;

    public Integer getValue() {
        return value;
    }

    ServiceCode(Integer value) {
        this.value = value;
    }

}