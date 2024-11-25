package iot.tyl.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {

	private int pId;
	private String pName;
	private String pInfo;
	private String imagePath;
	private int cId;
	private String cName;
	private List<ProductPrice> productPrice;
	private int qty;
//	private int aId;
//	private String aName;
//	private int aPrice;
//	private String size;
//	private double price;
//	private double total;
	private Timestamp creatTime;
	
}
