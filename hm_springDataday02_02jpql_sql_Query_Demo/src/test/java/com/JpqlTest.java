package com;


import com.dao.CustomerDao;
import com.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class JpqlTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindByName() {
        Customer customer = customerDao.findByName("凌巧");
        System.out.println(customer);
    }

    @Test
    public void testFindCustomerByNameAndId() {
        Customer customer = customerDao.findCustomerByNameAndId("凌巧",2l);
        System.out.println(customer);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(4l,"凌巧大帅哥");
    }

    //==================================================================

    @Test
    public void testFindSql() {
        List<Object[]> list = customerDao.findSql();
        for(Object [] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindSqlByName() {
        List<Object[]> list = customerDao.findSqlByName("凌巧");
        for(Object [] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    //======================================================================

    //使用方法名称规则来查询：
    @Test
    public void testFindByCustName() {
        Customer c = customerDao.findByCustName("凌巧");
        System.out.println(c);
    }

    //使用方法名称规则来模糊查询：
    @Test
    public void testFindByCustNameLike() {
        List<Customer> customers = customerDao.findByCustNameLike("凌巧");
        for (Customer customer:customers) {
            System.out.println(customer);
        }
    }

    //使用方法名称规则来多条件查询：
    @Test
    public void testFindByCustNameLikeAndCustIndustry() {
        List<Customer> customers = customerDao.findByCustNameLikeAndCustIndustry("凌巧","互联网行业");
        for (Customer customer:customers) {
            System.out.println(customer);
        }
    }


}
