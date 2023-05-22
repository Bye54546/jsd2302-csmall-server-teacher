package cn.tedu.csmall.passport.ex;

import cn.tedu.csmall.passport.web.ServiceCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public class ServiceException extends RuntimeException {

    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

}
