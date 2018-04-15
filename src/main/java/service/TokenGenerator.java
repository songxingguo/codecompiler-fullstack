package service;
/**  
* @Title: TokenGenerator.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月12日 下午3:34:16
*/


import java.util.ArrayList;
import java.util.List;

import dao.Entry;
import utils.Constant;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.12
 */
public class TokenGenerator {
	private static int token = 0;
	
	public static List<Entry> limiterword = generator(Constant.LIMITERWORD);
	public static List<Entry> keywords = generator(Constant.KEYWORDS);
	public static List<Entry> constant = generator(Constant.CONSTANT);
	public static List<Entry> arithmeticOperator = generator(Constant.ARITHMETIC_OPERATOR);
	public static List<Entry> relationOperator = generator(Constant.RELATION_OPERATOR);
	public static List<Entry> bitwiseOperator = generator(Constant.BITWISE_OPERATOR);
	public static List<Entry> logicalOperator = generator(Constant.LOGICAL_OPERATOR);
	public static List<Entry> assignmentOperator = generator(Constant.ASSIGNMENT_OPERATOR);
	public static List<Entry> conditionalOperator = generator(Constant.CONDITIONAL_OPERATOR);
	public static List<Entry> identifier = generator(Constant.IDENTIFIER);
	
	private static List<Entry> generator(String[] arr) {
		List<Entry> list = new ArrayList<Entry>();
		
		for (String element : arr) {
			token++;
			list.add(new Entry(element, token));
		}
		
		return list;
	}
	
	/**
	 * 
	 * @Title: getToken
	 * @Description: 获取最后一个token值
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public static int getLastToken() {
		int temp = token + 1;
		token++;
		return temp;
	}
	
	public static void main(String[] args) {
		for (Entry element : constant) {
			System.out.println(element.getWord() + ", "  + element.getToken());
		}
		
		for (Entry element : keywords) {
			System.out.println(element.getWord() + ", "  + element.getToken());
		}
	}
}
