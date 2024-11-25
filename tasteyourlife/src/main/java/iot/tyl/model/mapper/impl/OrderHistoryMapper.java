package iot.tyl.model.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import iot.tyl.model.CartEntity;
import iot.tyl.model.Category;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.OrderHistory;
import iot.tyl.model.OrderHistoryResponse;
import iot.tyl.model.Product;
import iot.tyl.model.ProductAddEntity;
import iot.tyl.model.ProductPrice;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.ProductSaleEntity;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.util.contant.Status;

@Component
public class OrderHistoryMapper  implements Mapper<List<OrderHistory>, List<OrderHistoryResponse>>{

	@Override
	public List<OrderHistoryResponse> toDto(List<OrderHistory> enties) {
		List<OrderHistoryResponse> list = new ArrayList<>();
		//id=key,value=index
		Map<Integer, Integer> mapKey = new HashMap<>();
		for (OrderHistory entity : enties) {
			int id = entity.getOrderId();
			OrderHistoryResponse listElement;
			List<ProductResponse> productInfos;
			int qty = 0;
			double totalPrice = 0;
			if (mapKey.containsKey(id)) {
				int index = mapKey.get(id);
				listElement = list.get(index);
				productInfos = listElement.getProductInfo();
				qty = listElement.getQty();
				totalPrice = listElement.getTotalPrice();
			} else {
				System.out.println("not in");
				mapKey.put(id, list.size());
				listElement = new OrderHistoryResponse();
				list.add(listElement);
				OrderEntity orderEntity = entity.getOrder();
				listElement.setId(entity.getOrderId());
				listElement.setPayment(orderEntity.getPaymentType().getDescription());
				listElement.setPaymentFee(orderEntity.getPaymentFee());
				listElement.setShipping(orderEntity.getShippingType().getDescription());
				listElement.setShippingFee(orderEntity.getShippingFee());
				listElement.setName(orderEntity.getName());
				listElement.setPhone("0" + orderEntity.getPhone());
				listElement.setEmail(orderEntity.getEmail());
				listElement.setAddress(orderEntity.getAddress());
				String orderStatus = entity.getPay() == Status.Y ? "已付款" : "未付款";
				listElement.setOrderStatus(orderStatus);
				listElement.setTime(entity.getCreateTime());
				listElement.setPaymentStatus(orderEntity.getStatus());
				productInfos = new ArrayList<>();
				totalPrice += orderEntity.getPaymentFee() + orderEntity.getShippingFee();
			}
			ProductResponse response = new ProductResponse();
			CartEntity cartEntity = entity.getCart();
			ProductSaleEntity saleEntity = cartEntity.getSale();
			ProductAddEntity addEntity = saleEntity.getProductAdd();
			Product p = saleEntity.getProduct();
			Category c = p.getCategory();
			response.setPId(p.getProductId());
			response.setPName(p.getProductName());
			response.setPInfo(p.getProductInfo());
			response.setImagePath(p.getPath());
			response.setCId(c.getCategoryId());
			response.setCName(c.getCategoryName());
			int theQty = cartEntity.getQty();
			qty += theQty;
			response.setQty(theQty);
			List<ProductPrice> listPrice = new ArrayList<>();
			ProductPrice pp = new ProductPrice();
			pp.setSId(saleEntity.getSaleId());
			pp.setSize(saleEntity.getSize());
			pp.setAId(addEntity.getAddId());
			pp.setAName(addEntity.getAddName());
			pp.setAPrice(addEntity.getAddPrice());
			double theTotalPrice = saleEntity.getPrice(); 
			
			pp.setPrice(theTotalPrice);
			listPrice.add(pp);
			response.setProductPrice(listPrice);
			productInfos.add(response);
			listElement.setQty(qty);
			totalPrice += theTotalPrice * theQty;
			listElement.setTotalPrice(totalPrice);
			listElement.setProductInfo(productInfos);
		}
		return list;
	}
	
	@Override
	public List<OrderHistory> toEntity(List<OrderHistoryResponse> dto) {
		return null;
	}
}
