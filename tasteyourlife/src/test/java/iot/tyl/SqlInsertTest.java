package iot.tyl;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.util.contant.BloodType;
import iot.tyl.util.contant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@SpringBootTest
public class SqlInsertTest {

	@Autowired
	private TestJpaDao testJpaDao;
	
	@Autowired
	private TestJpa testJpa;
	
// test only id	
//	@Test
//	public void test() {
//		TestEntity e = new TestEntity();
//		TestEntity get = testJpaDao.insert(e);
//		System.out.println(get);
//	}
	
//	@Test
//	public void test2() {
//		TestEntity e = new TestEntity();
//		TestEntity get = testJpaDao.insert(e);
//		System.out.println(get);
//		
//		e = new TestEntity();
//		e.setBloodtype(BloodType.O);
//		get = testJpaDao.insert(e);
//		System.out.println(get);
//		
//		e = new TestEntity();
//		e.setBloodtype(BloodType.N);
//		get = testJpaDao.insert(e);
//		System.out.println(get);
//		
//		e = new TestEntity();
//		e.setBloodtype(BloodType.A);
//		get = testJpaDao.insert(e);
//		System.out.println(get);
//		
//		e = new TestEntity();
//		e.setBloodtype(BloodType.B);
//		get = testJpaDao.insert(e);
//		System.out.println(get);
//		
//		e = new TestEntity();
//		e.setBloodtype(BloodType.AB);
//		get = testJpaDao.insert(e);
//		System.out.println(get);
//	}
	
	@Test
	public void test3() {
		List<TestEntity> es = testJpa.findAll();
		for(TestEntity e : es) {
			System.out.println(e);
		}
	}
}

@Repository
class TestJpaDao {
	
	@Autowired
	private TestJpa testJpa;
	
	public TestEntity insert(TestEntity testEntity) {
		return testJpa.save(testEntity);
	}
}

interface TestJpa extends JpaRepository<TestEntity, String>{
	
}

@Entity
@Data
@Table(name = "test")
class TestEntity {
	@Id
	@Column(name = "id", length = 36, nullable = false, updatable = false)
	private String id;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "bloodtype", length = 2, nullable = true)
    private BloodType bloodtype = BloodType.N;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status", length = 1, nullable = true)
    private Status status = Status.N;
	
	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID().toString();
	}
}