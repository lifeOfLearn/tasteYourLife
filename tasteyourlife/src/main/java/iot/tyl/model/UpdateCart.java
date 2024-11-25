package iot.tyl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCart {
	private int sid;
	private int qty;
}
