/**  
* @Title: Constants.java
* @Package utils
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午9:54:49
*/
package utils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.07
 */
public class Constant {
  public static final char EMPTY_CHAR = '\0';
  public static final char SPACE = '\t';
  
  /**
   * 分界符
   */
//  public static final String LIMITERWORD = ",.;()";
  
  public static String[] LIMITERWORD = //分界符  
  {",", ".", ";", "(", ")", "'", "\"", "", "//", "/*", "*/", "{", "}"}; 
  
  public static final String[] KEYWORDS = //关键字  
  {"abstract", "boolean", "break", "byte","case", "catch", "char", 
   "class", "continue", "default", "do","double", "else", "extends", 
   "final", "finally", "float", "for","if", "implements", "import", 
   "instanceof", "int", "interface", "long", "native", "new", "package", 
   "private", "protected", "public", "return", "short", "static", "super", 
   "switch","synchronized", "this", "throw","throws", "transient", "try",
   "void","volatile","while","strictfp","enum","goto","const","assert"};  
  
  public static final String[] CONSTANT = //常数
  {"intType", "decimalType", "charType", "strType", "scientific"};
  
  public static final String[] ARITHMETIC_OPERATOR = //算数运算符
  {"+", "-", "*", "/", "%", "++", "--"};	 
		  
  public static  final String[] RELATION_OPERATOR = //关系运算符
  {"==", "!=", ">", "<", ">=", "<="};
  
  public static final String[] BITWISE_OPERATOR = //位运算符
  {"&", "|", "^", "~", "<<", ">>", ">>>"};
  
  public static final String[] LOGICAL_OPERATOR = //逻辑运算符
  {"&&", "||", "!"};
  
  public static final String[] ASSIGNMENT_OPERATOR = //赋值运算符
  {"=", "+=", "-=", "*=", "/=", "%=", "<<=", ">>=", "&=", "^=", "|="};	  
  
  public static final String[] CONDITIONAL_OPERATOR = //条件运算符
  {"?:"};
  
  public static final String[] IDENTIFIER = //标识符
  {"var", "fun", "cla"};
} 
