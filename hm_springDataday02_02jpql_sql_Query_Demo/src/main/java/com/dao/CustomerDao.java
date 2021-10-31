package com.dao;

import com.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 符合SpringDataJpa的dao层接口规范
 *      JpaRepository<操作的实体类类型，实体类中主键属性的类型>
 *          * 封装了基本CRUD操作
 *      JpaSpecificationExecutor<操作的实体类类型>
 *          * 封装了复杂查询（分页）
 */
public interface CustomerDao extends JpaRepository<Customer, Long> , JpaSpecificationExecutor<Customer> {

    /**
     * 根据客户名查询客户
     *      使用jpql的方式查询
     *      jpql：from Customer where custName = ?
     */
    @Query(value = "from Customer where custName = ?")
    public Customer findByName(String custName);

    /**
     * 案例：根据客户名称和客户id查询客户
     *      jpql： from Customer where custName = ? and custId = ?
     *
     *  对于多个占位符参数
     *      赋值的时候，默认的情况下，占位符的位置需要和方法参数中的位置保持一致
     *
     *  可以指定占位符参数的位置
     *      ? 索引的方式，指定此占位的取值来源
     */
    @Query(value = "from Customer where custName = ?1 and custId = ?2")
    public Customer findCustomerByNameAndId(String name,Long id);

    /**
     * 使用jpql完成更新操作
     *      使用id更新，客户名称
     *     sql ：update cst_customer set cust_name = ? where cust_id = ?
     *     jpql：update  Customer set custName = ? where custId = ?
     *
     * @Query 是用于查询，而不是更新，怎么办？
     *      在下面加注解@Modifying
     *
     */
    @Query(value = "update  Customer set custName = ?2 where custId = ?1")
    @Modifying
    public void updateCustomer(long custId,String custName);

    //============================================================================

    /**
     * 使用sql查询全部
     * sql：select * from cst_customer
     */
    @Query(value = "select * from cst_customer",nativeQuery = true)
    public List<Object [] > findSql();

    /**
     * 模糊查询
     * @param name
     * @return
     */
    @Query(value="select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object [] > findSqlByName(String name);

    //======================================================================================

    /**
     * 使用方法名称规则来查询：
     *      （findBy+对象中的属性名）:查询
     *          对象中的属性名（首字母大写）：查询的条件
     *          findByCustName():根据客户名称查询
     *
     */
    public Customer findByCustName(String custName);

    /**
     *  使用方法名称规则来模糊查询：
     * @param custName
     * @return
     */
    public List<Customer> findByCustNameLike(String custName);

    /**
     * 使用客户名模糊查询和客户所属行业精准查询
     */
    public List<Customer> findByCustNameLikeAndCustIndustry(String custName,String custIndustry);

}