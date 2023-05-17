package cn.tedu.csmall.product.web;

public enum ServiceCode {

    OK(20000),
    ERR_BAD_REQUEST(40000),
    ERR_NOT_FOUND(40400),
    ERR_CONFLICT(40900),
    ERR_UNKNOWN(99999)
    ;

    private Integer value;

    public Integer getValue() {
        return value;
    }

    ServiceCode(Integer value) {
        this.value = value;
    }

}