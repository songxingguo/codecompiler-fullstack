/**  
* @Title: Lexical.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月6日 下午3:39:34
*/
package lexicalanalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.HeaderTokenizer.Token;

/**
 * <p>词法分析器</p>
 * @author songxinggo
 * @date 2018.04.06
 */
public class Lexical {
	
	private static final char[] CHARS = {'\n', '\0', '\t'};
    public static String buffer = null;//存储读取出来的字符串  
    public static int typenum = 0;  
     
    public static String[] type = //0表示无种类，1代表关键字，2代表算术符号，3代表关系运算符，4代表分界符，5代表数字，6代表标识符  
    {"无","关键字","算术符号","关系运算符","分界符","数字","标识符"};  
     
    public static String[] keywords = //保留字  
    {"abstract", "boolean", "break", "byte","case", "catch", "char", 
     "class", "continue", "default", "do","double", "else", "extends", 
     "final", "finally", "float", "for","if", "implements", "import", 
     "instanceof", "int", "interface", "long", "native", "new", "package", 
     "private", "protected", "public", "return", "short", "static", "super", 
     "switch","synchronized", "this", "throw","throws", "transient", "try",
     "void","volatile","while","strictfp","enum","goto","const","assert"};  
     
    public static String[] operateword = //算术符号  
    {"+","-","*","/"};  
     
    public static String[] relationword = //关系符号  
    {">","<","<=",">=","<>","=",":"};  
     
    public static String[] limiterword = //分界符  
    {":=",",",".",";","(",")"}; 
    
    public static List<Symbol> symbols = null;
    
    public List<Token> tokens = null;
	
	public void read(String filePath) {
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
	}
	
	/**
	 * @return 
	 * @Title: init
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public static List<Map<String, String>> init(String filePath) {
		List<Map<String, String>> tokens = new ArrayList<Map<String, String>>();
		
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
         
        	    String lineTxt = null;
                int lines = 0;
                int cols = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                	lines++;  
                    cols = 0;
                	
                    char c = '\0';
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
                bufferedReader.close();
                read.close();
            } 
            else {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        
        return tokens;
	}


    /**
     * 
     * @Title: isKeyword
     * @Description: 判断是否是关键字
     * @param: @param str
     * @param: @return
     * @return: int   
     * @throws
     */
    public int isKeyword(String str) {
    	int token = -1;
    	for (int i = 0; i < keywords.length; i++) {
    		if (keywords[i] == str) {
    			token = i + 1;
    			break;
    		}
    	}
    	return token;
    }
    
    /**
     * 
     * @Title: isExistSym
     * @Description: 判断标识符是否存在
     * @param: @param str
     * @param: @return
     * @return: int   
     * @throws
     */
    private  int isExistSym(String str) {
    	for (Symbol sym : symbols) {
    		if (sym.getName().equals(str)) {
    			return sym.getToken();
    		}
    	}
    	return -1;
    }
    
    /**
     * 
     * @Title: writeSym
     * @Description: 将标识符写入文件
     * @param: @param filePath
     * @return: void   
     * @throws
     */
    private void writeSym(String filePath) {
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
 
            for (Symbol sym : symbols) {
                writer.write(sym.toString());
                writer.newLine();
            }
            
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @Title: writeSym
     * @Description: 将tokens写入文件
     * @param: @param filePath
     * @return: void   
     * @throws
     */
    private void writeToken(String filePath) {
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
 
            for (Token token : tokens) {
                writer.write(token.toString());
                writer.newLine();
            }
            
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	     
    public int isoperateword(String s) {  
       int flag = 0;  
       for(int j = 0;j < operateword.length;j++) {  
           if(operateword[j].equals(s)) {  
               flag = 9 + j;  
               break;  
           }  
       }  
       return flag;  
    }  
	
    /**
     * 
     * @Title: isrelationword
     * @Description: 判断是否为关系符号,是，则返回种别码;不是，则返回0. 
     * @param: @param s
     * @param: @return
     * @return: int   
     * @throws
     */
    public int isrelationword(String s) {  
       int flag = 0;  
       for(int j = 0;j < relationword.length;j++) {  
           if(relationword[j].equals(s)) {  
               flag = 13 + j;  
               break;  
           }  
       }  
       return flag;  
    }  
	     
    /**
     * 
     * @Title: islimiterword
     * @Description: 判断是否为分界符,是，则返回种别码;不是，则返回0.
     * @param: @param s
     * @param: @return
     * @return: int   
     * @throws
     */
    public int islimiterword(String s) {  
       int flag = 0;  
       for(int j = 0;j < limiterword.length;j++) {  
           if(limiterword[j].equals(s)) {  
               flag = 19 + j;  
               break;  
           }  
       }  
       return flag;  
    }  
   
    /**
     * 
     * @Title: analysis
     * @Description: 对字符串进行分析(排除了界符，关系符，算术符号)  
     * @param: @param p
     * @param: @param s
     * @param: @return
     * @return: int   
     * @throws
     */
    public int analysis(int p,String s) {
       int sign = 0;  
       boolean flag = false;//标志变量，用来判断是否为数字  
       if(isreservedword(s) >= 0)//是否为保留字  
       {  
           sign = isreservedword(s);  
           typenum = 1;  
       }  
       else  
       {  
           for(int k = 0;k<s.length();k++)//是否为数字串  
           {  
               if(s.substring(k,k+1).compareTo("0") >= 0 && s.substring(k,k+1).compareTo("9") <= 0)  
               {  
                   flag = true;//如果一直是数字，则为真  
               }  
               else  
               {  
                   flag = false;  
                   break;//当有一个不位数字，则为假  
               }  
           }  
           if(flag)  
           {  
               sign = 27;  
               typenum = 5;  
           }  
           else//标识符  
           {  
               sign = 26;  
               typenum = 6;  
           }  
       }  
       return sign;  
    }  

    public void Test() {//词法分析主程序  
       read("Lexical.java");//读取文件  
       String Str1 = null;  
       int p = 0,typenum1 = 0;  
       int sign = 0,num = 0;  
       boolean flag1 = true,flag2 = false;  
       do  
       {     
           flag2 = false;//标识关系两个字节的关系运算符  
           Str1 = null;//存储提取的一段字符串  
           num = 0;//存储种别码  
           while(!buffer.substring(p,p+1).equals(" ") && !buffer.substring(p,p+1).equals("\n"))  
           //读取buffer中的字符，直到遇到换行符或者空格  
           {  
               if(islimiterword(buffer.substring(p,p+1)) > 0)//遇到分界符  
               {  
                   typenum1 = 4;  
                   num = islimiterword(buffer.substring(p,p+1));  
                   break;  
               }  
               if(isoperateword(buffer.substring(p,p+1)) > 0)//遇到算术符号  
               {  
                   typenum1 = 2;  
                   num = isoperateword(buffer.substring(p,p+1));  
                   break;  
               }  
               if(isrelationword(buffer.substring(p,p+1)) > 0)//遇到关系运算符  
               {  
                   if(buffer.substring(p,p+1).equals(":"))  
                   {  
                       if(buffer.substring(p+1,p+2).equals("="))//遇到:=分界符  
                       {  
                           typenum1 = 4;  
                           num = 25;  
                           break;  
                       }  
                   }  
                   typenum1 = 3;  
                   if(isrelationword(buffer.substring(p,p+2)) > 0)//遇到两个字节的关系运算符  
                   {  
                       flag2 = true;  
                       num = isrelationword(buffer.substring(p,p+2));  
                   }  
                   else//一个字节的关系运算符  
                   {  
                       num = isrelationword(buffer.substring(p,p+1));  
                   }  
                   break;  
               }  
               if(buffer.substring(p,p+1).equals("#"))//遇到结束符号  
               {  
                   flag1 = false;//flag1表示着是否遇到结束符号  
                   break;  
               }  
               if(Str1 == null)//这是为了区别当Str1为null时，使用+=会造成字符串中多处一个null  
               {  
                   Str1 = buffer.substring(p,p+1);  
               }  
               else  
               {  
                   Str1 += buffer.substring(p,p+1);  
               }  
               p++;  
                 
           }  
             
           if(Str1 != null)//当提取的字符串不为空时  
           {  
               sign = analysis(p,Str1);//分析字符串的种别码  
               System.out.println("<"+sign+","+Str1+","+type[typenum]+">");  
           }  
           if(num > 0)//当为特殊符号时(分界符，算术符号，关系运算符)  
           {  
               if(num == 25)  
               {  
                   System.out.println("<25,:=,分界符>");  
                   p = p + 2;  
                   continue;  
               }  
               if(flag2)//两个字节的关系运算符  
               {  
                   System.out.println("<"+num+","+buffer.substring(p,p+2)+","+type[typenum1]+">");  
                   p++;  
               }  
               else  
               {  
                   System.out.println("<"+num+","+buffer.substring(p,p+1)+","+type[typenum1]+">");  
               }  
           }  
           p++;  
       }while(flag1);  
       System.out.println("<0,#>");  
    }
	
	/**
	 * 
	 * @Title: isWord
	 * @Description:判断是否为单词
	 * @param: @param c
	 * @param: @return
	 * @return: boolean   
	 * @throws
	 */
	public static boolean isWord(char ch) {
		
		for (int i = 0; i < CHARS.length; i++) {
			
			if (CHARS[i] == ch) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @Title: getLine
	 * @Description: 根据文件位置和读取的行数读取指定行的数据
	 * @param: 
	 * @return: String   
	 * @throws
	 */
	public static String getLine(String filePath, int lineNumber) {
	    String lineTxt = null;
        
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
         
                int lines = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                	lines++;  
                	
                	if (lines == lineNumber) {
                		break;
                	}
                	 
                }
                bufferedReader.close();
                read.close();
            } 
            else {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        
        return lineTxt;
	}
	
	/**
	 * 
	 * @Title: getLine
	 * @Description: 获取指定文件的每一行并存入List中
	 * @param: @param filePath
	 * @param: @return
	 * @return: List<String>   
	 * @throws
	 */
	public static List<String> getLine(String filePath) {
	    List<String> list = new ArrayList<String>();
	    try {
	        String encoding = "GBK";
	        File file = new File(filePath);
	        if (file.isFile() && file.exists()) { // 判断文件是否存在
	            InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file), encoding);// 考虑到编码格式
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	
	            while ((lineTxt = bufferedReader.readLine()) != null)
	            {
	                list.add(lineTxt);
	            }
	            bufferedReader.close();
	            read.close();
	        }
	        else {
	            System.out.println("找不到指定的文件");
	        }
	    }
	    catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
	
	    return list;
	}
	
	/**
	 * @Title: getNextChar
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public static char getNextChar(String s, int number) {
		return s.charAt(2);
	}

	/**
	 * @Title: sort
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public static int sort(char ch) {
		if (isDigit(ch)) {
			return DIGIT;
		} else if (isAlpha(ch)){
			return ALPAHA;
		} else if (ch == '/') {
			
		} else if (ch == '/') {
			
		} else if (isDelimeter(ch)) {
			
		} else {
			return OTHER;
		}
	}

	/**
	 * @Title: recogId
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogId() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: recogDig
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogDig() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: recogStr
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogStr() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: handCom
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void handCom() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: recogDel
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogDel() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: lookupKeyword
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void lookupKeyword() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: lookupSym
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void lookupSym() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: insertSym
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void insertSym() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: outputToken
	 * @Description: TODO
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void outputToken() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 
	 * @Title: main
	 * @Description: 词法分析器的入口函数
	 * @param: @param args
	 * @return: void   
	 * @throws
	 */
	public static void main(String[] args) {
		Lexical l = new Lexical();
		l.Test();
	}
}
