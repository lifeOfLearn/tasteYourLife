package iot.tyl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerData {
	private String name;
	private String phone;
	private String mail;
	private String address;
}
