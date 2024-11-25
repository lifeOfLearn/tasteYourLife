package iot.tyl.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String user;
	private String tokenId;
}
