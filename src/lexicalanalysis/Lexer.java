/**  
* @Title: ExicalAnalysis.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午9:19:58
*/
package lexicalanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.mail.internet.HeaderTokenizer.Token;

import utils.Constant;

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
	
	//标识符
    public static List<Symbol> symbols = null;
    
    public List<Token> tokens = null;
	
	/**
	 * 
	 * @Title: scanner
	 * @Description: 词法分析主函数对源程序进行扫描分析
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public void scanner() {
		try {
            String encoding = "UTF-8";
            File file = new File("Lexical.java");
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
         
                String str = null;
    			while ((str = bufferedReader.readLine()) != null) {
    				lines++;
    				cols = 0;
    	            buffer = str;

    		        while (buffer != null) {
//    		        	printPosition();
        	            //首字符决定单词的处理
    		        	sort(getChar());
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
	}
	
	/**
	 * 
	 * @Title: sort
	 * @Description: 根据单词的首字符以决定对不同类单词的处理
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void sort(char ch) {
		if (isAlpha(ch)) {
			//识别标识符
			recogId(ch);
		} else if (isDigit(ch)) {
			//识别数
			recogDig(ch);
		} else if (ch == '/') {
			//处理注释
			handCom();
		} else if (isDelimeter(ch)) {
			//识别定界符
			recogDel();
		} else if (ch == '\"') {
			//识别字符常数
			recogStr();
		} else {
			
		}
	}
	
	/**
	 * 
	 * @Title: isAlpha
	 * @Description: 字符是否为单词
	 * @param: @param ch
	 * @param: @return
	 * @return: boolean   
	 * @throws
	 */
	private boolean isAlpha(char ch) {
		return (ch>= 65 && ch <= 90) || (ch >= 97 && ch <=122);
	}
	
	/**
	 * 
	 * @Title: isDigit
	 * @Description: 字符是否为数字
	 * @param: @param ch
	 * @param: @return
	 * @return: boolean   
	 * @throws
	 */
	private boolean isDigit(char ch) {
		return ch >= 48 && ch <= 57;
	}
	
	/**
	 * 
	 * @Title: isDelimeter
	 * @Description: 是否为定界符
	 * @param: @param ch
	 * @param: @return
	 * @return: boolean   
	 * @throws
	 */
	private boolean isDelimeter(char ch) {
		return Constant.LIMITERWORD.indexOf(ch) > 0;
	}
	
	/**
	 * 
	 * @Title: getChar
	 * @Description: 获取一个字符
	 * @param: @return
	 * @return: boolean   
	 * @throws
	 */
	private char getChar() {
		char ch = Constant.EMPTY_CHAR;
		cols++;
		
		if (buffer.length() > 0) {
	    	ch = buffer.charAt(0);
	    	buffer = buffer.substring(1);
		} else {
			buffer = null;
		}
		
		return ch;
	}
	
	/**
	 * @Title: recogId
	 * @Description: 识别标识符
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void recogId(char ch) {
		String str = "";
	
		do {
			str += String.valueOf(ch);
			ch = getChar();
		} while((isAlpha(ch)));
		
		//列计数器回退一个
		cols--;
		
		int token = -1;
		if ((token = isKeyword(str)) > 0) {
			printInfo(str, "关键字", token);
		} 
		else {
			if ((token = isExistSym(str)) > 0) {
				printInfo(str, "标志符", token);
			} else {
				printInfo(str, "标志符", insertSym(str));
			}
		}
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
    	for (int i = 0; i < Constant.KEYWORDS.length; i++) {
    		if (Constant.KEYWORDS[i] == str) {
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
    	if (symbols == null) return -1;
    	
    	for (Symbol sym : symbols) {
    		if (sym.getName().equals(str)) {
    			return sym.getToken();
    		}
    	}
    	return -1;
    }
    
    /**
     * 
     * @Title: insertSym
     * @Description: 填入符号表
     * @param: @param str
     * @param: @param token
     * @param: @return
     * @return: int   
     * @throws
     */
    private int insertSym(String str) {
    	return new Symbol(str).getToken();
    }

	/**
	 * @Title: recogDig
	 * @Description: 识别数
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void recogDig(char ch) {
		String str = "";
		
		while (isDigit(ch) || ch == '.' ||  ch == 69) {
			str += String.valueOf(ch);
			ch = getChar();
		}
		
		//列计数器回退一个
		cols--;
		
		int token = 0;
		printInfo(str, "常数", token);
	}

	/**
	 * @Title: recogStr
	 * @Description: 识别字符常数
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogStr() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: handCom
	 * @Description: 处理注释
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void handCom() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @Title: recogDel
	 * @Description: 识别界符的函数
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private static void recogDel() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @Title: printInfo
	 * @Description: 打印单词信息
	 * @param: @param type
	 * @param: @param token
	 * @return: void   
	 * @throws
	 */
	private void printInfo(String name, String type, int token) {
		System.out.println("{name: " + name +", type: " + type + ", token: " + token + "}");
	}
	
	/**
	 * 
	 * @Title: printPosition
	 * @Description: 打印位置信息
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	public void printPosition() {
		System.out.println("(" + lines + ", " + cols + ")");
	}
	
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		lexer.scanner();
	}
}
