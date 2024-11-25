package iot.tyl.service;

import java.util.List;

import iot.tyl.model.CheckoutRequest;
import iot.tyl.model.OrderHistoryResponse;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.UpdateCart;

public interface OrderService {

	public void addCart(String tokenId, int sId, int qty);
	public List<ProductResponse> getCart(String tokenId);
	public void updateCart(String tokenId, List<UpdateCart> requestList);
	public int getQuantity(String tokenId);
	public boolean checkout(String tokenId, CheckoutRequest request);
	public List<OrderHistoryResponse> getOrderHistory(String tokenId, String date);
}
