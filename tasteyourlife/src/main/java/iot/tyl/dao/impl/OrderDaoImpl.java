package iot.tyl.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import iot.tyl.dao.CartDaoJpa;
import iot.tyl.dao.OrderDao;
import iot.tyl.dao.OrderDaoJpa;
import iot.tyl.dao.OrderHistoryDaoJpa;
import iot.tyl.model.CartEntity;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.OrderHistory;
import iot.tyl.util.contant.Status;

@Repository
public class OrderDaoImpl implements OrderDao{

	@Autowired
	private CartDaoJpa cartDaoJpa;
	
	@Autowired
	private OrderDaoJpa orderDaoJpa;
	
	@Autowired
	private OrderHistoryDaoJpa orderHistoryDaoJpa;
	
	@Override
	public void insertCart(CartEntity entity) {
		cartDaoJpa.save(entity);
	}

	@Override
	public List<CartEntity> getCart(String clientId) {
		return cartDaoJpa.findByCustomer_ClientIdAndChooseAndCheckoutOrderBySale_SaleId(clientId, Status.Y, Status.N);
	}
	
	@Override
	public void updateCart(List<CartEntity> entities) {
		cartDaoJpa.saveAll(entities);
	}

	@Override
	public CartEntity getCaryByTheOne(String userId, int saleId) {
		return cartDaoJpa
					.findByCustomer_IdAndSale_SaleIdAndChooseAndCheckout(
							userId,
							saleId,
							Status.Y,
							Status.N);
	}
	
	@Override
	public void addCart(String clientId, int sId) {
		cartDaoJpa.findByCustomer_ClientIdAndChooseAndCheckoutOrderBySale_SaleId(clientId, Status.Y, Status.N);
		
	}

	@Override
	public OrderEntity addOrder(OrderEntity entity) {
		OrderEntity resultEntity = orderDaoJpa.save(entity);
		return resultEntity;
	}
	
	@Override
	public void saveHistroies(List<OrderHistory> entities) {
		orderHistoryDaoJpa.saveAll(entities);
	}
	
	@Override
	public List<OrderHistory> getOrderHistory(String userId, LocalDateTime time) {
		return orderHistoryDaoJpa.findByOrder_Customer_IdAndCreateTimeAfter(userId, time);
	}
}
