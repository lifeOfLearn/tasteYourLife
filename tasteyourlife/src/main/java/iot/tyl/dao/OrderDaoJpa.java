package iot.tyl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.OrderEntity;

@Repository
public interface OrderDaoJpa extends JpaRepository<OrderEntity, Integer>{

}
