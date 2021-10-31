package com;

import com.domain.Customer;
import com.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * 测试jpql查询
 */
public class JPQLTest {

    /**
     * 查询全部：
     *      jpql: from Customer
     *      sql：select * from cust_csutomer
     */
    @Test
    public void testFindAll(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object obj:list) {
            System.out.println(obj);
        }
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     *  倒叙查询--根据id倒叙
     *  sql：select * from cst_customer order by cust_id desc;
     *  jpql：from Customer order by custId desc;
     */
    @Test
    public void testOrders(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        String jpql = "from Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object obj:list) {
            System.out.println(obj);
        }
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 统计查询
     *  sql:select count(cust_id) from cst_customer;
     *  jpql:select count(custId) from Customer
     */
    @Test
    public void testCount(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        String jpql = "select count(custId) from Customer";
        Query query = entityManager.createQuery(jpql);
        //发送查询，并封装结果集
        Object singleResult = query.getSingleResult();
        System.out.println(singleResult);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     *  sql:select * from cst_customer limit ?,?
     *  jpql:from Customer
     */
    @Test
    public void testPage(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        String jpql = "from Customer";
            // 1、创建query查询对象
        Query query = entityManager.createQuery(jpql);
            //对参数赋值：分页参数（起始索引、最大查询条数）
        query.setFirstResult(0); //起始索引
        query.setMaxResults(2);//最大查询条数
            //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object obj:list) {
            System.out.println(obj);
        }
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     * 查询客户名称为凌巧的
     * sql：select * from cst_customer where cust_name like '凌巧%';
     * jpql：from Customer where custName like ?
     */
    @Test
    public void testCondition(){
        //1.获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.操作
        String jpql = "from Customer where custName like ?";
        // 1、创建query查询对象
        Query query = entityManager.createQuery(jpql);
        //对参数赋值：
        query.setParameter(1,"凌巧%");
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object obj:list) {
            System.out.println(obj);
        }
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }


}
