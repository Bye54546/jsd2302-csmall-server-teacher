package cn.tedu.csmall.passport.ex;

import cn.tedu.csmall.passport.web.ServiceCode;
import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

}
