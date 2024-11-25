package iot.tyl.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.OrderHistory;
import iot.tyl.model.OrderHistoryId;

@Repository
public interface OrderHistoryDaoJpa extends JpaRepository<OrderHistory, OrderHistoryId>{
	List<OrderHistory> findByOrder_Customer_IdAndCreateTimeAfter(String id, LocalDateTime time);
}
