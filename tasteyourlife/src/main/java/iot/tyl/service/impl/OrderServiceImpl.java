package iot.tyl.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iot.tyl.dao.CustomerDao;
import iot.tyl.dao.OrderDao;
import iot.tyl.dao.ProductDao;
import iot.tyl.model.CartEntity;
import iot.tyl.model.CheckoutRequest;
import iot.tyl.model.CustomerEntity;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.OrderHistory;
import iot.tyl.model.OrderHistoryResponse;
import iot.tyl.model.ProductData;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.ProductSaleEntity;
import iot.tyl.model.UpdateCart;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.service.OrderService;
import iot.tyl.service.RedisService;
import iot.tyl.util.CustomerUtil;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.contant.PaymentType;
import iot.tyl.util.contant.RedisMapKey;
import iot.tyl.util.contant.ShippingType;
import iot.tyl.util.contant.Status;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private Mapper<List<CartEntity>, List<ProductResponse>> mapper;
	
	@Autowired
	private Mapper<OrderEntity, CheckoutRequest> orderMapper;
	
	@Autowired
	private Mapper<List<OrderHistory>, List<OrderHistoryResponse>> historyMapper;
	
	
	@Autowired
	private CustomerUtil customerUtil;
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	@Override
	public void addCart(String tokenId, int sId, int qty) {
		String userId = getUserId(tokenId);
		checkId(sId, qty, userId);
		hasDataBySaleId(sId, tokenId);
		CartEntity entity = getCartData(userId, sId);
		if (entity == null) {
			entity = new CartEntity();
			entity.setCustomer(new CustomerEntity());
			entity.setSale(new ProductSaleEntity());
			entity.getCustomer().setId(userId);
			entity.getSale().setSaleId(sId);
			entity.setQty(qty);	
		} else {
			int total = entity.getQty() + qty;
			entity.setQty(total);
		}
		orderDao.insertCart(entity);
	}
	
	@Override
	public List<ProductResponse> getCart(String tokenId) {
		String clientId = getClientId(tokenId);
		List<CartEntity> carts = orderDao.getCart(clientId);
		return mapper.toDto(carts);
	}
	
	@Override
	public void updateCart(String tokenId, List<UpdateCart> requestList) {
		String clientId = getClientId(tokenId);
		Map<Integer, Integer> requestMap = new HashMap<>();
		requestList.forEach(data -> requestMap.put(data.getSid(), data.getQty()));
		List<CartEntity> carts = orderDao.getCart(clientId);
		carts.forEach(entity -> {
			int key = entity.getSale().getSaleId();
			Integer dataValue = requestMap.get(key);
			if (dataValue == null) {
				entity.setChoose(Status.N);
			} else {
				entity.setQty(dataValue);
			}
		});
		orderDao.updateCart(carts);
	}
	
	@Override
	public int getQuantity(String tokenId) {
		String clientId = getClientId(tokenId);
		List<CartEntity> carts = orderDao.getCart(clientId);
		int qty = 0;
		for (CartEntity cart : carts) {
			qty += cart.getQty();
		}
		return qty;
	}
	
	@Transactional
	@Override
	public boolean checkout (String tokenId, CheckoutRequest request) {
		String clientId = getClientId(tokenId);
		//String userId = getUserId(tokenId);
		checkData(request, clientId);
		OrderEntity entity = orderMapper.toEntity(request);
		
		CustomerEntity custoemrEntity = customerDao.getCustomerByClient(clientId);
		if (custoemrEntity == null)
			throw exceptionUtil.throwCheckoutGetUserNull(clientId);
		
		List<CartEntity> carts = orderDao.getCart(clientId);
		if (carts.size() == 0)
			return false;
		
		entity.setCustomer(custoemrEntity);
		OrderEntity resultEntity = orderDao.addOrder(entity);
		int orderId = resultEntity.getId();
		List<OrderHistory> histories = new ArrayList<>();
		for (CartEntity cart : carts) {
			cart.setCheckout(Status.Y);
			OrderHistory history = new OrderHistory();
//			OrderHistoryId idKey = new OrderHistoryId();
//			idKey.setOrderId(orderId);
//			idKey.setCartId(cart.getId());
			history.setOrderId(orderId);
			history.setCartId(cart.getId());
//			history.setId(idKey);
//			history.setOrder(resultEntity);
//			history.setCart(cart);
			histories.add(history);
		}
		try {
			orderDao.saveHistroies(histories);
			orderDao.updateCart(carts);
			return true;
		} catch (Exception e) {
			throw exceptionUtil.throwOrderCheckoutUnknow(clientId, e);
		}
	}
	
	@Override
	public List<OrderHistoryResponse> getOrderHistory(String tokenId, String date) {
		String userId = getUserId(tokenId);
		LocalDateTime time = stringDateTodateTime(date);
		List<OrderHistory> listEntities = orderDao.getOrderHistory(userId, time);
		return historyMapper.toDto(listEntities);
	}
	
	/**
	 * @param date
	 * @return LocalDateTime
	 * @throws DateTimeException
	 */
	private LocalDateTime stringDateTodateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date,formatter);
		return localDate.atStartOfDay();
	}
	
	private void checkData(CheckoutRequest request, String clientId) {
		String phone = request.getRecipientPhone();
		String email = request.getRecipientEmail();
		String payment = request.getPaymentType();
		String shipping = request.getShippingType();
		if (!customerUtil.checkPhone(phone) ) {
			throw exceptionUtil.throwOderRecipientPhone(clientId, phone);
		}
		if (!customerUtil.checkMail(email)) {
			throw exceptionUtil.throwOrderRecipientEmail(clientId, email);
		}
		PaymentType paymentType = PaymentType.getKey(payment);
		if (paymentType == null) {
			throw exceptionUtil.throwOrderPaymentType(clientId, payment);
		}
		ShippingType type = ShippingType.getkey(shipping);
		if (type == null) {
			throw exceptionUtil.throwOrderShppingType(clientId, shipping);
		}
		if (!paymentType.hasShippingType(type)) {
			throw exceptionUtil.throwOrderPaymentNoHasTheShipping(clientId, payment, shipping);
		}
	}
	
	private CartEntity getCartData(String userId, int sId) {
		return orderDao.getCaryByTheOne(userId, sId);
	}
	
	private boolean hasDataBySaleId(int sId, String tokenId) {
		ProductData data = productDao.getProductBySId(sId);
		if (data != null)
			return true;
		
		throw exceptionUtil.throwCartGetSIdNull(tokenId, sId);
	}
	private void checkId(int sId, int qty, String userId) {
		if (sId <= 0 || qty <= 0) {
			throw exceptionUtil.throwOrderDataInvalid(userId, sId, qty);
		}
			
			
	}
	private String getClientId(String tokenId) {
		String clientId = null;
		try {
			clientId = get(tokenId);
		} catch (Exception e) {
			
		} finally {
			if (clientId != null)
				return clientId;
		}
		throw exceptionUtil.throwCartGetRedisIdNull(tokenId);
	}
	private String getUserId(String tokenId) {
		String id = null;
		try {
			String clientId = get(tokenId);
			id = getUserByClientId(clientId);
		} catch (Exception e) {
			
		} finally {
			if (id != null)
				return id;
		}
		
		throw exceptionUtil.throwCartGetRedisIdNull(tokenId);
		
	}
	
	private String getUserByClientId(String clientId) {
		return (String)getMap(clientId).get(RedisMapKey.id.name());
	}
	private Map<Object,Object> getMap(String key){
		return redisService.getMap(key);
	}
	
	private String get(String key) {
		return redisService.getValue(key);
	}
}
