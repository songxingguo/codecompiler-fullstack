/**  
* @Title: Lexical.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月6日 下午3:39:34
*/
package lexicalanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>词法分析器</p>
 * @author songxinggo
 * @date 2018.04.06
 */
public class Lexical {
	
	/**
	 * 
	 * @Title: main
	 * @Description: 词法分析器的入口函数
	 * @param: @param args
	 * @return: void   
	 * @throws
	 */
	public static void main(String[] args) {
		init();
		
		System.out.println(getLine("testData.txt", 3));
		
		getNextChar();
		
		sort();
		
		recogId();
		
		recogDig();
		
		recogStr();
		
		handCom();
		
		recogDel();
		
		lookupKeyword();
		
		lookupSym();
		
		insertSym();
		
		outputToken();
	}	
}
