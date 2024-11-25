package iot.tyl.model;

import java.util.Date;

import iot.tyl.util.contant.BloodType;
import iot.tyl.util.contant.GenderType;
import iot.tyl.util.contant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class CustomerEntity {

	@Id
    @Column(name = "id", length = 36, nullable = false)
	private String id; 

	@Column(name = "client_id", length = 36, nullable = false)
	private String clientId; 
	 
	@Column(name = "token_id", length = 36, nullable = false)
	private String tokenId; 
	
    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "phone", length = 9, nullable = false, unique = true)
    private Integer phone;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "rocid", length = 10, nullable = false)
    private String rocid;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "bloodtype", length = 2, nullable = true)
    private BloodType bloodtype = BloodType.N;
    
    @Column(name = "address", length = 100, nullable = true)
    private String address = "";
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 1, nullable = true)
    private Status status = Status.N;
    
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime created_at;
//    
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime  updated_at;
    
    
    @Column(name = "gender", length = 1, nullable = false)
    private char genderChar = GenderType.NOSAY.getCode();
    
    @Transient
    private GenderType gender = GenderType.NOSAY;
    
    @PostLoad
    private void convertGender() {
        this.gender = GenderType.getGenderTypeFromCode(genderChar);
    }
    
    
//    @PrePersist
//	public void generateId() {
//		this.id = UUID.randomUUID().toString();
//		this.clientId = UUID.randomUUID().toString();
//		this.tokenId = UUID.randomUUID().toString();
//	}
    
    public String errLog() {
    	return new StringBuffer()
					.append(",name=").append(name)
					.append(",phone=").append(phone)
					.append(",email=").append(email)
					.toString();
    }

}
