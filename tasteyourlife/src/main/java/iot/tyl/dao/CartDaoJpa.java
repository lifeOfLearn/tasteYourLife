package iot.tyl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.CartEntity;
import iot.tyl.util.contant.Status;

@Repository
public interface CartDaoJpa extends JpaRepository<CartEntity, Integer>{
	List<CartEntity> findByCustomer_ClientIdAndChooseAndCheckoutOrderBySale_SaleId(String clientId, Status choose, Status checkout);
	CartEntity findByCustomer_IdAndSale_SaleIdAndChooseAndCheckout(String userId, int saleId, Status choose, Status checkout);
	
}
