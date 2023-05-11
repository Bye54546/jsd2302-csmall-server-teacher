package cn.tedu.csmall.product.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
    private Integer state;
    private String message;
}
