package iot.tyl.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CookieData {
	private String path;
	private String key;
	private long timeSec;
}
