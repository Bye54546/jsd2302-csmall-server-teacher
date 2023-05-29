package cn.tedu.csmall.product.web;

import cn.tedu.csmall.commons.web.ServiceCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一响应结果类型
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@Accessors(chain = true)
public class JsonResult implements Serializable {

    /**
     * 操作结果的状态码（状态标识）
     */
    @ApiModelProperty("业务状态码")
    private Integer state;
    /**
     * 操作失败时的提示文本
     */
    @ApiModelProperty("提示文本")
    private String message;
    /**
     * 操作成功时响应的数据
     */
    @ApiModelProperty("数据")
    private Object data;

    /**
     * 生成表示"成功"的响应对象
     *
     * @return 表示"成功"的响应对象
     */
    public static JsonResult ok() {
        return ok(null);
    }

    /**
     * 生成表示"成功"的响应对象，此对象中将包含响应到客户端的数据
     *
     * @param data 响应到客户端的数据
     * @return 表示"成功"的响应对象
     */
    public static JsonResult ok(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }

    /**
     * 生成表示"失败"的响应对象
     *
     * @param serviceCode 业务状态码
     * @param message     提示文本
     * @return 表示"失败"的响应对象
     */
    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
