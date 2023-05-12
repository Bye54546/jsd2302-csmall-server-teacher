package cn.tedu.csmall.product.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JsonResult implements Serializable {

    private Integer state;
    private String message;

    public static JsonResult ok() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK.getValue());
        return jsonResult;
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
