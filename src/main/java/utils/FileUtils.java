/**  
* @Title: FileUtils.java
* @Package utils
* @Description: TODO
* @author songxingguo
* @date 2018年4月6日 下午3:44:37
*/
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.06
 */
public class FileUtils {
	public static String read(String filePath) {
		String buffer = null;
		try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
         
                String str = null;
    			while ((str = bufferedReader.readLine()) != null) {
    				if (buffer == null) {
    					buffer = str;
    				} else {
    					buffer += str;
    				}
    				
    				buffer += '\n';
    			}
                bufferedReader.close();
                read.close();
            } 
            else {
                System.out.println("找不到指定的文件");
            }
		} catch (Exception e){
            System.out.println("读取文件内容出错");
            e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * 
	 * @Title: readByLine
	 * @Description: 按行读取文件
	 * @param: @param filePath
	 * @param: @return
	 * @return: String   
	 * @throws
	 */
	public static String readByLine(String filePath, int line) {
		String lineStr = null;
		try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
         
                String str = null;
                int lines = 0;
    			while ((str = bufferedReader.readLine()) != null) {
    				lines++;
    				
    				if (line == lines) {
    					lineStr = str;
    					break;
    				}
    			}
                bufferedReader.close();
                read.close();
            } 
            else {
                System.out.println("找不到指定的文件");
            }
		} catch (Exception e){
            System.out.println("读取文件内容出错");
            e.printStackTrace();
		}
		return lineStr;
	}
	
	public static <E> void writeToFile(List<E> list) {
		for(int i = 0;i<array.size();i++) {
            Goods g =  array.get(i);
             File file = new File("d://goodsInfo.txt");
              FileOutputStream fos1 = new FileOutputStream(file, true);
                // 创建FileOutputStream对应的PrintStream，方便操作。PrintStream的写入接口更便利
                PrintStream out1 = new PrintStream(fos1);
                String str=""+g.getName()+","+g.getBarcode()+","+g.getPrice()+"\r\n";
                out1.print(str);
                out1.close();
        }
		
		
	}
}
