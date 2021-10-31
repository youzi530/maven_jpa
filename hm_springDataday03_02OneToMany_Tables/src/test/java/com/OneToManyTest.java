package com;

import com.dao.CustomerDao;
import com.dao.LinkManDao;
import com.domain.Customer;
import com.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 保存一个客户，保存一个联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd(){
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        /**
         * 配置了客户到联系人的关系，这样外键才可以关联
         *      从客户的角度上：（控制台看）发送两条insert语句，发送一条更新语句更新数据库（更新外键）
         * 由于我们配置了客户到联系人的关系：客户可以对外键进行维护
         */
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 配置联系人到客户的关系（多对一）
     *    只发送了两条insert语句（控制台看）
     * 由于配置了联系人到客户的映射关系（多对一）
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd1(){
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        /**
         * 配置联系人到客户的关系（多对一）
         *      只发送了两条insert语句
         */
        linkMan.setCustomer(customer);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 会有一条多余的update语句
     *      * 由于一的一方可以维护外键：会发送update语句
     *      * 解决此问题：只需要在一的一方放弃维护权即可
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd2(){
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        /**
         * 配置联系人到客户的关系（多对一）
         *
         */
        linkMan.setCustomer(customer);//由于配置了多的一方到一的一方的关联关系（当保存的时候，就已经对外键赋值）
        customer.getLinkMans().add(linkMan);//由于配置了一的一方到多的一方的关联关系（发送一条update语句）

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 级联添加:当保存一个客户的时候同时保存所有联系人
     *     1、需要区分操作的主体
     *
     *     2、需要再操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
     *     3、cascade（配置级联）
     *          @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
     *          private Set<LinkMan> linkMans = new HashSet<LinkMan>();
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd(){
        Customer customer = new Customer();
        customer.setCustName("百度1");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李1");

        linkMan.setCustomer(customer);//由于配置了多的一方到一的一方的关联关系（当保存的时候，就已经对外键赋值）
        customer.getLinkMans().add(linkMan);//由于配置了一的一方到多的一方的关联关系（发送一条update语句）

        customerDao.save(customer);
    }


    /**
     * 级联添加:当删除一个客户，将他相关的联系人全部删除
     *     1、需要区分操作的主体
     *
     *     2、需要再操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
     *     3、cascade（配置级联）
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDelete(){
        //查询一号客户
        Customer customer = customerDao.findOne(1l);
        //删除一号客户
        customerDao.delete(customer);
    }
}
