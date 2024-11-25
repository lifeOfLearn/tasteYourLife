package iot.tyl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.dao.OrderHistoryDaoJpa;
import iot.tyl.model.CartEntity;
import iot.tyl.model.OrderEntity;
import iot.tyl.model.OrderHistory;
import iot.tyl.model.OrderHistoryId;
import iot.tyl.util.contant.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@SpringBootTest
public class OrderHistoryTest {

	@Autowired
	private OrderHistoryDaoJpaTest orderHistoryDaoJpa;
	
	@Test
	public void testJpa() {
		String id = "f2bc7d2f-c629-476c-be14-97927910c44d";
		LocalDateTime time = LocalDateTime.of(2024, 11, 3, 0, 0, 0); 
		List<OrderHistory> list = orderHistoryDaoJpa.findByOrder_Customer_IdAndCreateTimeAfter(id,time);
		list.forEach(e -> {
			System.out.println(e.toString());
		});
	}
}
@Repository
interface OrderHistoryDaoJpaTest extends JpaRepository<OrderHistory, OrderHistoryId>{
	List<OrderHistory> findByOrder_Customer_IdAndCreateTimeAfter(String id, LocalDateTime time);
	List<OrderHistory> findByOrder_Customer_Id(String id);
	List<OrderHistory> findByCreateTimeAfter(LocalDateTime time);
}

@Data
@Entity
@IdClass(OrderHistoryId.class)
@Table(name = "order_history")
class OrderHistoryEntityTest {
	
	@Id
	private int orderId;
	
	@Id
	private int cartId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "pay", nullable = false)
	private Status pay = Status.N;
	
	@OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL)
//	@MapsId("orderId")
	//@JoinColumn(name = "order_id", referencedColumnName = "id" , nullable = false, insertable = false, updatable = false)
	private List<OrderEntity> order;
	
	@OneToOne
//	@MapsId("cartId")
	@JoinColumn(name = "cart_id", nullable = false, insertable = false, updatable = false)
	private CartEntity cart;
	
	@Column(name = "create_time", nullable = false, insertable = false, updatable = false)
	private Timestamp createTime;
}