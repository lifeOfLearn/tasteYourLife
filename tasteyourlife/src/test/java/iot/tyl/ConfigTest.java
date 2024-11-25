package iot.tyl;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import iot.tyl.config.ErrLogMsgConfig;
import iot.tyl.config.ErrMsgConfig;
import iot.tyl.config.ParamConfig;
import iot.tyl.config.PathConfig;

@SpringBootTest
public class ConfigTest {

	@Autowired
	private ParamConfig paramConfig;
	
	@Autowired
	private PathConfig pathConfig;
	
	@Autowired
	private ErrMsgConfig errMsgConfig;
	
	@Autowired
	private ErrLogMsgConfig errLogMsgConfig;
	
	
	@Value("${server.ssl.key-alias}")
	private String keyAlias;
	
	@Test
	public void test() {
		System.out.println("START");
		
		Object[] objs = {paramConfig, pathConfig,
						 errMsgConfig, errLogMsgConfig};
		
		for (Object obj : objs) {
			reflect(obj);
		}
		
		
		System.out.println("END");
		
		System.out.println("keyAlias : " + keyAlias);
	}
	
	public void reflect(Object obj) {
		//JVM 設定未命名的model訪問, 參數如下
		//--add-opens java.base/java.lang=ALL-UNNAMED
		try {
			if (obj == null) return;
			
			System.out.println("check : " + obj.getClass().getSimpleName());
			startReflect(obj, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	public void startReflect(Object obj, int level) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {

			field.setAccessible(true);
			
			try {
				Object fieldValue = field.get(obj);
				String tab = "\t".repeat(level);
				
				if (fieldValue != null && isBaseType(field.getType())) {
					System.out.println(tab + field.getName() + " : ");
					startReflect(fieldValue, level + 1);
				} else {
					System.out.println(tab + field.getName() + " : " + fieldValue);
				}
				
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				System.out.println("讀取屬性值失敗");
				e.printStackTrace();
				break;
			}//try
		}//for
	}
	
	private static boolean isBaseType(Class<?> type) {
		 return type.getPackageName().startsWith("iot.tyl.config"); 
    }
}
