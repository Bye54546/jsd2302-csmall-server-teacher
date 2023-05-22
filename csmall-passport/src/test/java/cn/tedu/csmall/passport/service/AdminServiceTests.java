package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.pojo.param.AdminAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    void addNew() {
        AdminAddNewParam adminAddNewParam = new AdminAddNewParam();
        adminAddNewParam.setUsername("测试用户名0003");
        adminAddNewParam.setPassword("测试密码0003");
        adminAddNewParam.setDescription("测试简介0003");
        adminAddNewParam.setRoleIds(new Long[]{3L, 4L, 6L});

        try {
            service.addNew(adminAddNewParam);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println("添加失败！出现了某种异常！");
            e.printStackTrace();
        }
    }


}
