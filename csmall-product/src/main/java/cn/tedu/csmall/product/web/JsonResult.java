package cn.tedu.csmall.product.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JsonResult implements Serializable {

    private Integer state;
    private String message;

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
