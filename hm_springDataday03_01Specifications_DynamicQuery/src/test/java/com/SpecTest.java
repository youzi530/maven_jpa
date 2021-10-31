package com;

import com.dao.CustomerDao;
import com.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    @Autowired
    private CustomerDao customerdao;

    /**
     * 根据条件，查询单个对象
     */
    @Test
    public void testSpec(){
        //匿名内部类：
        /**
         * 自定义查询条件:
         *      1、实现Specification接口（提供泛型，查询的对象类型）
         *      2、实现toPredicate方法（构造查询条件）
         *      3、需要借助方法参数中的两个参数：
         *          root：获取需要查询的对象属性
         *          CriteriaBuilder:构造查询条件的，内部封装了很多的查询条件（模糊匹配、精准匹配）
         *
         * 案例：根据客户名查询，查询客户名为传智播客的客户
         *      1、查询条件:
         *          查询方式:CriteriaBuilder:
         *              Predicate pr = criteriaBuilder.equal(custName, "凌巧");
         *          比较的属性名称:root
         *              Path<Object> custName = root.get("custName");
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1、获取比较的属性名称:root
                Path<Object> custName = root.get("custName");
                //2、构造查询方式:CriteriaBuilder:    select * from cst_customer where cust_name = '凌巧'
                Predicate pr = criteriaBuilder.equal(custName, "凌巧");//进行精准匹配（比较的属性，比较的属性值）
                return pr;
            }
        };
        Customer customer = customerdao.findOne(spec);
        System.out.println(customer);
    }


    /**
     * 多条件的查询
     *      案例：根据客户名和客户名称来查询
     *      1、 比较的属性名称:root
     *          custName：客户名
     *          custIndustry：客户行业
     *      2、查询方式:CriteriaBuilder:
     *          构造客户名的精准匹配查询
     *          构造所属行业的精准匹配查询
     *          将以上两个查询联系起来
     */
    @Test
    public void  testSpec1(){
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1、比较的属性名称:root
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");
                //2、查询方式:CriteriaBuilder:
                Predicate p1 = criteriaBuilder.equal(custName, "凌巧");
                Predicate p2 = criteriaBuilder.equal(custIndustry, "互联网行业");
                //将多个查询条件组合一起：
                Predicate and = criteriaBuilder.and(p1, p2);
                return and;
            }
        };
        Customer customer = customerdao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 案例：完成根据客户名称的模糊查询，返回客户列表
     *      客户名称：“凌巧”
     *
     *  CriteriaBuilder:里面的方法
     *      equal:直接的到path（属性），然后进行比较即可
     *      gt、lt、ge、le、like：得到path对象，再根据path指定比较的参数类型，再去进行比较
     *          指定参数的类型：path.as(类型的字节码对象)
     */
    @Test
    public void testSpec3(){
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1、比较的属性名称:root
                Path<Object> custName = root.get("custName");
                //2、查询方式:CriteriaBuilder:
                Predicate like = criteriaBuilder.like(custName.as(String.class),"凌巧%");
                return like;
            }
        };
        List<Customer> customers = customerdao.findAll(spec);
        for (Customer c:customers
             ) {
            System.out.println(c);
        }
    }

    /**
     * 按照顺序的查询
     */
    @Test
    public void testSpec4(){
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1、比较的属性名称:root
                Path<Object> custName = root.get("custName");
                //2、查询方式:CriteriaBuilder:
                Predicate like = criteriaBuilder.like(custName.as(String.class),"凌巧%");
                return like;
            }
        };
        /**
         *  创建排序对象,需要调用构造方法实例化对象
         *      第一个参数：排序的顺序
         *          Sort.Direction.DESC
         *          Sort.Direction.ASC
         *      第二个参数：排序的属性名称
         *
         */
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> customers = customerdao.findAll(spec,sort);
        for (Customer c:customers
        ) {
            System.out.println(c);
        }
    }

    /**
     *  分页查询
     *      findAll(Specification,  Pageable);
     *          Specification：查询条件
     *          Pageable：分页参数：查询的页码、每页查询的条数
     *      返回:Page(springDataJpa为我们封装好了pageBean对象，数据列表，共条数)
     *
     *      创建PageRuqest的过程中，需要调用他的构造方法传入两个参数：
     *          第一个参数：当前查询的页数
     *          第二个参数：每页查询的数量
     */
    @Test
    public void testSpec5(){
        Specification specification = null;
        Pageable pageable = new PageRequest(0,2);
        Page<Customer> page = customerdao.findAll(null, pageable);
        System.out.println(page.getTotalElements()); //得到总条数

        System.out.println(page.getTotalPages());//得到总页数
        System.out.println(page.getContent());//得到数据集合列表
    }




}
