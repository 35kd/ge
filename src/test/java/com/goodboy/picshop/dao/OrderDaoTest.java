package com.goodboy.picshop.dao;

import com.goodboy.picshop.BaseTest;
import com.goodboy.picshop.dto.OrderDto;
import com.goodboy.picshop.entity.Commodity;
import com.goodboy.picshop.entity.Order;
import com.goodboy.picshop.entity.Receiving;
import com.goodboy.picshop.entity.User;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OrderDao的测试类
 */
public class OrderDaoTest extends BaseTest {

    //注入OrderDao
    @Autowired
    private OrderDao orderDao;

    //注入CommodityDao
    @Autowired
    private CommodityDao commodityDao;

    //注入ReceivingDao
    @Autowired
    private ReceivingDao receivingDao;

    //注入UserDao
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsertOrder() throws Exception{
        User user = userDao.queryUserById(1);
        Receiving receiving = receivingDao.queryDefaultReceiving(1);
        Commodity commodity = commodityDao.queryCommodityById(1);
        Order order = new Order("No000003", new Date(), user, receiving, commodity);
        int insert = orderDao.insertOrder(order);
        System.out.println("insert = " + insert);
    }

    @Test
    public void testQueryOrderById() throws Exception{
        Order order = orderDao.queryOrderById(1);
        System.out.println(order);
    }

    // 查找买家的订单
    @Test
    public void testQueryBuyerOrderByUserId() throws Exception{
        List<Order> orderList = orderDao.queryBuyerOrderById(3, 0, 5, null);
        System.out.println(orderList);
        System.out.println(orderList.get(0).getCreateTime());
    }

    // 根据是否付款来查找用户的订单信息
    @Test
    public void testQueryOrderByUserIdAndIsPay() throws Exception{
        List<Order> orderList = orderDao.queryOrderByUserIdAndIsPay(1, 0, 0, 5);
        System.out.println(orderList);
    }

    // 根据订单id删除订单信息
    @Test
    public void testDeleteOrder () {
        int result = orderDao.deleteByOrderId(57, 6);
        if (result == 1) {
            System.out.println("删除订单成功");
        }
    }

    // 测试： cherubic新的订单记录
    @Test
    public void testCreateOrders () {
        List<Order> list = new ArrayList<>();

        User user = userDao.queryUserById(6);
        Receiving receiving = new Receiving(); receiving.setId(3);
        Commodity commodity = new Commodity(); commodity.setId(4);
        Order o1 = new Order("No000006", new Date(), user, receiving, commodity);

        User user2 = userDao.queryUserById(6);
        Receiving receiving2 = new Receiving(); receiving2.setId(2);
        Commodity commodity2 = new Commodity(); commodity2.setId(2);
        Order o2 = new Order("No000007", new Date(), user2, receiving2, commodity2);

        orderDao.insertOrder(o1);
        orderDao.insertOrder(o2);
    }

    // 测试：用户6支付：订单6
    @Test
    public void testPay () {
        int result = orderDao.pay(6, 6);
        System.out.println(result);
    }


	// 测试：查询卖家拥有的订单列表
	@Test
	public void testQuerySellerOrders () {
    	List<Order> orders = orderDao.querySellerOrderBySellerID(4, 0, 4, null);
		System.out.println(orders);
	}

	// 测试：卖家为指定的订单发货
	@Test
	public void testSendComm() {
    	int status = 1;
    	int orderid = 52;
    	int result = orderDao.handlerOrder( status, orderid );
		System.out.println(result);
	}

	// 测试：卖家查询未发货订单
	@Test
	public void testQueryOrdersUnSent () {
    	// 未发货
    	// List<Order> list = orderDao.querySellerOrderBySellerID(4, 0, 20, 0);
		// 已发货
		List<Order> list = orderDao.querySellerOrderBySellerID(4, 0, 20, 1);
		System.out.println(list);
		System.out.println("结果有" + list.size() + "条记录");
	}

	// 测试：指定的订单是否已经付款
	@Test
	public void testCheckPay () {
    	Order order= orderDao.queryOrderById(8);
		System.out.println(order);
	}

}