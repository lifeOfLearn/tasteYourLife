package iot.tyl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPrice {
	private int sId;
	private int aId;
	private String aName;
	private int aPrice;
	private String size;
	private double price;
}
