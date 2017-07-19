package store.utils;

/**
 * 目录分离算法：
 *  用于将上传文件自动分别存放到不同的文件夹下
 *  返回值demo:4/14/2/2/
 * @author yang
 *
 */
public class UploadDirectoryUtils {
	public static String getDividePath(String fileName){
		StringBuilder sb = new StringBuilder();
		int code = fileName.hashCode();
		int d;
		for (int i = 0; i < 4; i++) { //总共搞四层目录(此处最多可以设置8层:因为int共4字节,可以按位右移4次)
			d = code & 0xf;  //1级目录（取值范围：0~15）：按位与运算
			sb.append(d+"/");  //
			code  = code >>> 4;   // 三个大于号表示无符号右移
		}
		return sb.toString();
	}
}
