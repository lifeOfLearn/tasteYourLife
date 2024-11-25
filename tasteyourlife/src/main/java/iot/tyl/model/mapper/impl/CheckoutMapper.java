package iot.tyl.model.mapper.impl;

import org.springframework.stereotype.Component;

import iot.tyl.model.CheckoutRequest;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.mapper.Mapper;
import iot.tyl.util.contant.PaymentType;
import iot.tyl.util.contant.ShippingType;

@Component
public class CheckoutMapper implements Mapper<OrderEntity, CheckoutRequest> {

	@Override
	public CheckoutRequest toDto(OrderEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//進來前自己先檢查資料沒問題再說
	@Override
	public OrderEntity toEntity(CheckoutRequest dto) {
		OrderEntity entity = new OrderEntity();
		PaymentType paymentType = PaymentType.getKey(dto.getPaymentType());
		ShippingType shippingType = ShippingType.getkey(dto.getShippingType());
//			if (!paymentType.hasShippingType(shippingType)) {
//				shippingType = null;
//			}
		entity.setPaymentType(paymentType);
		entity.setPaymentFee(paymentType.getFee());
		entity.setPaymentNote(dto.getPaymentNote());
		entity.setShippingType(shippingType);
//			if (shippingType != null) {
//				entity.setShippingFee(shippingType.getFee());
//			}
		entity.setShippingFee(shippingType.getFee());
		entity.setAddress(dto.getRecipientAddress());
		entity.setName(dto.getRecipientName());
		entity.setPhone(Integer.valueOf(dto.getRecipientPhone().substring(1) ) );
		entity.setEmail(dto.getRecipientEmail());
		return entity;
	}
}
