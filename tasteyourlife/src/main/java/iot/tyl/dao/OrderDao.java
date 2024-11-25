package iot.tyl.dao;

import java.time.LocalDateTime;
import java.util.List;

import iot.tyl.model.CartEntity;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.OrderHistory;

public interface OrderDao {
	public void insertCart(CartEntity entity);
	public void addCart(String clientId, int sId);
	public void updateCart(List<CartEntity> entities);
	public List<CartEntity> getCart(String clientId);
	public CartEntity getCaryByTheOne(String userId, int saleId);
	public OrderEntity addOrder(OrderEntity entity);
	public void saveHistroies(List<OrderHistory> entities);
	public List<OrderHistory> getOrderHistory(String userId, LocalDateTime time);
}
