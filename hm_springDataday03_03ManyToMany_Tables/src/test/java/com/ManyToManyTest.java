package com;

import com.dao.RoleDao;
import com.dao.UserDao;
import com.domain.Role;
import com.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一个用户，保存一个角色
     *
     * 多对多放弃维护权，被动的一方放弃维护权
     *  放弃role里面的维护关系
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd(){
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        user.getRoles().add(role);//配置用户到角色的关系，可以对中间表中的数据进行维护 1-1
        role.getUsers().add(user);//配置角色到用户的关系，可以对中间表中的数据进行维护 1-1

        userDao.save(user);
        roleDao.save(role);
    }

    /**
     * 级联添加
     *      保存用户的时候，保存用户关联的角色
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd(){
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        user.getRoles().add(role);//配置用户到角色的关系，可以对中间表中的数据进行维护 1-1
        role.getUsers().add(user);//配置角色到用户的关系，可以对中间表中的数据进行维护 1-1

        userDao.save(user);
    }

    /**
     * 级联删除
     *      删除id为1的用户，同时删除关联的角色
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDelete(){
        User user = userDao.findOne(1l);

        userDao.delete(user);
    }


}
