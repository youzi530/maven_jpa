package com.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决实体管理器工厂浪费资源和耗时问题
 *      通过静态代码块的形式，当程序第一次访问工具类的时候，创建一个公共的实体管理器工厂对象
 *    第一次访问getEntityManager方法：经过静态代码块创建一个factory对象，再调用方法创建一个EntityManager
 *    第二次访问getEntityManager方法：经过已经创建好的facotry对象，创建EntityManager对象
 *
 */
public class JpaUtils {

    private static EntityManagerFactory factory; //只创建一次

    static {
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取EntityManager对象
     */
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
