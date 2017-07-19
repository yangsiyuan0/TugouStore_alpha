package store.utils;


import java.util.UUID;

/**
 * 传入一个文件名，将随机字符串与其进行拼接，使得文件名无重复问题
 * @author yang
 *
 */
public class UUIDFilenameUtils {
	public static String getUUID(){
		return  UUID.randomUUID().toString().replace("-", "");
	}
	public static String getUUIDName(String filename){
		return getUUID()+"_"+filename;
	}
}
