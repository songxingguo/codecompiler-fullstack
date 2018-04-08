/**  
* @Title: ExicalAnalysis.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午9:19:58
*/
package lexicalanalysis;

import java.util.HashMap;
import java.util.Map;

import utils.FileUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.07
 */
public class Lexer {
	
	//缓冲区用于保存一行数据
	private String buffer = null;
	//行计数器
	private int lines = 0;
	//列计数器
	private int cols = 0; 
	
	/**
	 * 
	 * @Title: scanner
	 * @Description: 词法分析主函数对源程序进行扫描分析
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public void scanner() {
		//读取文件第一行
		String buffer = FileUtils.readByLine("Lexical.java", 13);
		
		System.out.println(buffer);
		
	}
	
	/**
	 * 
	 * @Title: readChar
	 * @Description: 从缓冲区中读取一个非空字符
	 * @param: @return
	 * @return: char   
	 * @throws
	 */
	public char readChar() {
        while ((c = lineTxt.charAt(cols)) != '\0') {
        	cols++;
        	sort(c);
        	
        	if (isWord(c)) {
            	String word = "";
            	String token = "";
            	Map<String, String> tokenMap = new HashMap<String, String>();
            	tokenMap.put(word, token);
        		tokens.add(tokenMap);
        	}
        }
	}
	
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		lexer.scanner();
	}
}
