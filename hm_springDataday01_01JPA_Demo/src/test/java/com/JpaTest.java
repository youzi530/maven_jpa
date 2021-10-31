package com;

import com.domain.Customer;
import com.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    //测试保存到数据库

    /**
     * jpa操作步骤：
     *      1、加载配置文件创建工厂（实体管理类工厂）对象
     *      2、通过实体管理类工厂获取实体管理器
     *      3、获取事务对象，开启事务
     *      4、完成增删改查操作
     *      5、提交事务（回滚事务）
     *      6、资源关闭
     */
    @Test
    public void testSave(){
        //1、加载配置文件创建工厂（实体管理类工厂）对象
        //EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        //2、通过实体管理类工厂获取实体管理器
        //EntityManager em = factory.createEntityManager();
        EntityManager em = JpaUtils.getEntityManager();

        //3、获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction(); //获取事务对象
        tx.begin();
        //4、完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("凌巧3");
        customer.setCustIndustry("互联网行业");
        em.persist(customer);
        //5、提交事务（回滚事务）
        tx.commit();
        //6、资源关闭
        em.close();
    }


    /**
     * 根据id查询用户
     * find:两个参数：
*          ·第一个参数class，查询数据的结果需要包装实体类类型的字节码
*           ·第二个参数id，查询的主键的取值
     *
     * 使用find方法查询：立即加载
     *         查询的对象就是当前的客户对象本身，在调用find方法时候，就会发送sql语句查询数据库
     */
    @Test
    public void testFind(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
            //·第一个参数class，查询数据的结果需要包装实体类类型的字节码
            //·第二个参数id，查询的主键的取值
        Customer customer = entityManager.find(Customer.class, 1l);
        System.out.println(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 根据id查询用户
     *
     * 使用getRefrence方法查询：延迟加载
     *         查询的对象是动态代理对象，调用getRefrence方法的时候，不会立即发送sql语句查询数据库
     *          当调用查询结果对象的时候，才会发送查询的sql语句：什么时候用，什么时候就发送sql查询数据库
     */
    @Test
    public void testReference(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        //·第一个参数class，查询数据的结果需要包装实体类类型的字节码
        //·第二个参数id，查询的主键的取值
        Customer customer = entityManager.getReference(Customer.class, 1l);
        System.out.println(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 删除客户的案例
     */
    @Test
    public void testRemove(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
            //根据id查询客户
            Customer customer = entityManager.find(Customer.class, 1l);
            //调用删除操作
            entityManager.remove(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 更新客户的案例
     */
    @Test
    public void testUpdate(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
            //根据id查询出数据
            Customer customer = entityManager.find(Customer.class, 2l);
            //更新客户
            customer.setCustAddress("湖南");
            entityManager.merge(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }
}
