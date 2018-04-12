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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Symbol> symbols = new ArrayList<Symbol>();
    
    public List<Token> tokens = new ArrayList<Token>();
    
    public List<Entry> entrys = new ArrayList<Entry>();
	
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
            File file = new File("testData.txt");
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
			handCom(ch);
		} else if (isDelimeter(String.valueOf(ch))) {
			//识别定界符
			recogDel(ch);
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
	private boolean isDelimeter(String str) {
		for (String word : Constant.LIMITERWORD) {
			if (word == str) {
				return true;
			}
		}
		return false;
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
	
//	private char getChar() { 
//		if (cols > buffer.length()) {
//			return Constant.EMPTY_CHAR;
//		}
//
//		return buffer.charAt(cols);
//	}
	
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
			insToken(str, token);
			printInfo(str, "关键字", token);
		} 
		else {
			if ((token = isExistSym(str)) > 0) {
				printInfo(str, "标志符", token);	
			} else {
				insToken(str, token);
				printInfo(str, "标志符", insertSym(str, TokenGenerator.getLastToken()));
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
    private int isKeyword(String str) {
    	return getToken(TokenGenerator.keywords, str);
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
    		if (sym.getName().getWord().equals(str)) {
    			return sym.getToken();
    		}
    	}
    	
    	return -1;
    }
    
    /**
     * 
     * @Title: insertSym
     * @Description: 向 token字表中填入内容
     * @param: @param str
     * @param: @param token
     * @param: @return
     * @return: int   
     * @throws
     */
    private int insertSym(String word, int token) {
    	Symbol symbol = new Symbol(word, token);
    	symbols.add(symbol);
    	return symbol.getToken();
    }
    
    /**
     * 
     * @Title: writeSym
     * @Description: 将符号表的内容写到文件中
     * @param: 
     * @return: void   
     * @throws
     */
    private void writeSym() {
		String fileName = "symbolTable.txt";
		for (Symbol sym : symbols) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(fileName), true);
				PrintStream out = new PrintStream(fos);
				String str = "" + sym.getName().getWord() + "   " + sym.getToken() + "\r\n";
				out.print(str);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 
     * @Title: insToken
     * @Description: 将 token字表的内容写到文件中
     * @param: @param str
     * @param: @return
     * @return: int   
     * @throws
     */
    private void insToken(String word, int token) {
    	Token to = new Token(word, token);
    	tokens.add(to);
    }
    
    /**
     * 
     * @Title: writeToken
     * @Description: TODO
     * @param: @return
     * @return: int   
     * @throws
     */
    private void writeToken() {
		String fileName = "tokenTable.txt";
		for (Token token : tokens) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(fileName), true);
				PrintStream out = new PrintStream(fos);
				String str = "" + token.getWord() + "   " + token.getToken() + "\r\n";
				out.print(str);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 
     * @Title: getToken
     * @Description: 获取对应字符的token值
     * @param: @param entryList
     * @param: @param str
     * @param: @return
     * @return: int   
     * @throws
     */
    private int getToken(List<Entry> entryList, String str) {
		for (Entry e : entryList) {
			if (e.getWord().equals(str)) {
				return  e.getToken();
			}
		}
		
		return -1;
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
		
		if (str.indexOf(".") > 0) {
			printInfo(str, "小数", getToken(TokenGenerator.constant, "decimalType"));
		} else if (str.indexOf("E") > 0){
			printInfo(str, "科学计数", getToken(TokenGenerator.constant, str));
		} else {
			printInfo(str, "常数", getToken(TokenGenerator.constant, "intType"));
		}
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
	private void handCom(char ch) {
		String str = "";
		str += String.valueOf(ch);
		//注释
		if ((ch = getChar()) == '*') {
			int length = str.length();
			while (length >= 2 && str.substring(length - 2, length - 1) == "*/") {
				str += String.valueOf(ch);
				ch = getChar();
			}
			
			int token = 0;
			printInfo(str, "注释", token);
		} else {
			//列计数器回退一个
			cols--;
			int token = 0;
			printInfo(str, "/", token);
		}
	}

	/**
	 * @Title: recogDel
	 * @Description: 识别界符的函数
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void recogDel(char ch) {
		String str = "";
		str += String.valueOf(ch);
		str += String.valueOf(getChar());
		if (isDelimeter(str)) {
			printInfo(str, "双界符", getToken(TokenGenerator.limiterword, str));
		} else {
			cols--;
			printInfo(str, "界符", getToken(TokenGenerator.limiterword, str));
		}
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
		System.out.println("{" + name +", " + type + ", " + token + "}");
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
		lexer.writeSym();
		lexer.writeToken();
	}
}
