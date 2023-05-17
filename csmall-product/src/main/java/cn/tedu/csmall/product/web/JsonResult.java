package cn.tedu.csmall.product.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JsonResult implements Serializable {

    private Integer state;
    private String message;
    private Object data;

    public static JsonResult ok() {
        return ok(null);
    }

    public static JsonResult ok(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
