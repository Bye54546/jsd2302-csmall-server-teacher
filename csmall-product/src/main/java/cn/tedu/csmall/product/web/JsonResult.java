package cn.tedu.csmall.product.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JsonResult implements Serializable {

    private ServiceCode state;
    private String message;

    public static JsonResult ok() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK);
        return jsonResult;
    }

    public static JsonResult fail(ServiceCode state, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(state);
        jsonResult.setMessage(message);
        return jsonResult;
    }

//    public JsonResult setState(Integer state) {
//        this.state = state;
//        return this;
//    }
//
//    public JsonResult setMessage(String message) {
//        this.message = message;
//        return this;
//    }
//
//    public Integer getState() {
//        return state;
//    }
//
//    public String getMessage() {
//        return message;
//    }
}
