package com;

import com.dao.CustomerDao;
import com.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class testJpaDao {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(4l);
        System.out.println(customer);
    }

    /**
     * save：保存或者更新操作
     *  根据传递的对象是否存在主键id，
     *      如果没有id主键属性：保存
     *      如果有id主键属性，根据id查询数据，再更新数据
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustName("傻逼");
        customer.setCustLevel("至尊");
        customerDao.save(customer);
    }

    /**
     * 如果有id主键属性，根据id查询数据，再更新数据
     *      但是会把原来的数据清空！！！
     */
    @Test
    public void testUpdate() {
        Customer customer = new Customer();
        customer.setCustName("傻逼");
        customer.setCustLevel("至尊");
        customer.setCustId(5l);
        customer.setCustIndustry("装逼界");
        customerDao.save(customer);
    }

    @Test
    public void testDelete () {
        customerDao.delete(5l);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> customers = customerDao.findAll();
        for (Customer customer:customers) {
            System.out.println(customer);
        }
    }

    /**
     * 测试统计查询：查询客户的总数量
     *      count:统计总条数
     */
    @Test
    public void testCount() {
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 测试：判断id为4的客户是否存在
     *      1. 可以查询以下id为4的客户
     *          如果值为空，代表不存在，如果不为空，代表存在
     *      2. 判断数据库中id为4的客户的数量
     *          如果数量为0，代表不存在，如果大于0，代表存在
     */
    @Test
    public void  testExists() {
        boolean exists = customerDao.exists(4l);
        System.out.println("id为4的客户 是否存在："+exists);
    }

    /**
     * 根据id从数据库查询
     *      @Transactional : 保证getOne正常运行
     *
     *  findOne：
     *      em.find()           :立即加载
     *      返回的是查询对象
     *      不管用不用，立即加载
     *  getOne：
     *      em.getReference     :延迟加载
     *      * 返回的是一个客户的动态代理对象
     *      * 什么时候用，什么时候查询
     */
    @Test
    @Transactional
    public void  testGetOne() {
        Customer customer = customerDao.getOne(4l);
        System.out.println(customer);
    }

}
