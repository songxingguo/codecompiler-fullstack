package service;
/**  
* @Title: ExicalAnalysis.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午9:19:58
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.Entry;
import dao.Symbol;
import dao.Token;
import utils.Constant;
import utils.FileUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.07
 */

public class Lexer {
	
	//缓冲区用于保存一行数据
	private char[] buffer = null;
	private String strBuffer = null;
	//行计数器
	private int lines = 0;
	//列计数器
	private int cols = 0; 
	
	//标识符
    public List<Symbol> symbols = new ArrayList<Symbol>();
    
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
         
    			while ((strBuffer = bufferedReader.readLine()) != null) {
    				lines++;
    				buffer = strBuffer.toCharArray();
    	
    				cols = 0;
    				char ch = ' ';
    		        while (!isEnding() && !isSpace(ch = getChar())) {
        	            //首字符决定单词的处理
    		        	sort(ch);
    		        }
    			}
    			
    			//向文件中写入符号表
    			writeSym();
    			//向文件中写入token表
    			writeToken();
    			
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
		} 
		else if (isDigit(ch)) {
			//识别数
			recogDig(ch);
		} 
		else if (ch == '/') {
			//处理注释
			handCom(ch);
		} 
		else if (isDelimeter(String.valueOf(ch))) {
			//识别定界符
			recogDel(ch);
		} 
		else if (ch == '\'') {
			//识别字符常数
			recogStr(ch);
		}
		else {
			error("出现不识别的字符", ch);
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
	 * 判断是否为字母或者数字
	 * @param ch
	 * @return
	 */
	private boolean isAlnum(char ch) {
		return isAlpha(ch) || isDigit(ch);
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
		List list = new ArrayList(Arrays.asList(Constant.LIMITERWORD));
        list.addAll(Arrays.asList(Constant.ARITHMETIC_OPERATOR));
        list.addAll(Arrays.asList(Constant.RELATION_OPERATOR));
        list.addAll(Arrays.asList(Constant.BITWISE_OPERATOR));
        list.addAll(Arrays.asList(Constant.LOGICAL_OPERATOR));
        list.addAll(Arrays.asList(Constant.ASSIGNMENT_OPERATOR));
        list.addAll(Arrays.asList(Constant.CONDITIONAL_OPERATOR));
        
        Object[] c = list.toArray();
        
		for (Object word : c) {
			if (word.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSign(String str) {
		for (String word : Constant.ARITHMETIC_OPERATOR) {
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
		return buffer[cols++];
	}
	
	/**
	 * 是否为缓冲区的最后一个字符
	 * @return
	 */
	private boolean isEnding() {
		return cols > buffer.length - 1;
	}
	
	/**
	 * 是否为空格
	 * @param ch
	 * @return
	 */
	private boolean isSpace(char ch) {
		return ch == ' ';
	}
	
	/**
	 * @Title: recogId
	 * @Description: 识别标识符
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void recogId(char ch) {		
		char state = '0';
		int start = cols - 1;
		outterLoop:
		while (!isEnding() && !isSpace(ch) && state != '2') {
			switch (state) {
			case '0': 
				if (isAlpha(ch)) {
					state = '1';
				} else {
					error("标识符错误", ch);
				} break;
			case '1': 
				if (isAlnum(ch)) {
					state = '1';
				} else {
					state = '2';
					cols--; //退回当前读入的字符
					break outterLoop;
				} break;
			}
			
			ch = getChar();
		}
		
		String str = getStr(start, cols);
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
	 * 返回错误信息
	 */
	private void error(String type, char value) {
		System.out.print(value + type + " at: ");
		printPosition();
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
//    	System.out.println(symbol.toString());
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
    public void writeSym() {
		String fileName = "symbolTable.txt";
		for (Symbol sym : symbols) {
			String str = "" + sym.getName().getWord() + "   " + sym.getToken() + "\r\n";
			FileUtils.writeToFile(fileName, str);
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
//    	System.out.println(to.toString());
    }
    
    /**
     * 
     * @Title: writeToken
     * @Description: TODO
     * @param: @return
     * @return: int   
     * @throws
     */
    public void writeToken() {
		String fileName = "tokenTable.txt";
		for (Token token : tokens) {
			String str = "" + token.getWord() + "   " + token.getToken() + "\r\n";
			FileUtils.writeToFile(fileName, str);
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
    private int getToken(List<Entry> list, String str) {
		for (Entry e : list) {
			if (str.trim().equals(e.getWord())) {
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
		int start = cols - 1;
		char state = '0';
		
		outterLoop: 
		while (!isEnding() && !isSpace(ch) && state != '7') {
			switch (state) {
			case '0':
				if (isDigit(ch)) {
					state = '1';
				} else {
					error("识别数出错1", ch);
				} break;
			case '1': 
				if (isDigit(ch)) {
					state = '1';
				} else if (ch == '.') {
					state = '2';
				} else if ((ch == 'e') || (ch == 'E')) {
					state = '4';
				} else {
					cols--; 
					state = '7';
					break outterLoop;
				} break;
			case '2':
				if (isDigit(ch)) {
					state = '3';
				}  else {
					error("识别数出错2", ch);
				} break;
			case '3': 
				if (isDigit(ch)) {
					state = '3';
				} else if ((ch=='E') || (ch=='e')) {
					state ='4';
				} else {
					cols--;
					state = '7';
					break outterLoop;
				} break;
			case '4':
				if (isDigit(ch)) {
					state = '6';
				} else if(isSign(String.valueOf(ch))) {
					state = '5';
				} else {
					error("识别数出错3", ch);
				} break;
			case '5': 
				if (isDigit(ch)) {
					state='6';
			    } else {
			    	error("识别数出错4", ch);
			    } break;
			case '6': 
				if (isDigit(ch)) {
					state = '6';
				} else {
					cols--;
					state = '7';
					break outterLoop;
				} break;
 			} 
			
			ch = getChar();
		}
		
		String str = getStr(start, cols);
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
	private void recogStr(char ch) {
		int start = cols -1;
		char state='0'; /*初始状态*/
		while (!isEnding() && !isSpace(ch) && state!='2') { 
			switch (state) {
		    case '0': state='1'; break;
		    case '1': 
		    	if (ch =='\'') {
		    		state = '2';
		    	} else {
		    	    state='1'; 
		    	} break;
		   }
			
		   ch = getChar();
		}
		
		String str = getStr(start, cols);
		printInfo(str, "字符常数", getToken(TokenGenerator.constant, "decimalType"));
	}

	/**
	 * @Title: handCom
	 * @Description: 处理注释
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void handCom(char ch) {
		int start = cols - 1;
		char state = '0';
		
		outterLoop:
		while (!isEnding() && !isSpace(ch) && state != '5') {
			switch (state) {
			case '0': state = '1'; break;
			case '1': 
				if (ch == '/') {
					state = '2';
				} else if(ch == '*') {
					state = '3';
				} else {
					state = '5';
					cols--;
					break outterLoop;
				} break;
			case '2': state = '2'; break;
			case '3':
				if (ch == '*') {
					state = '4';
				} else {
					state = '3';
				} break;
			case '4':
				if (ch == '/') {
					state = '5';
					break outterLoop;
				} else {
					state = '3';
				} break;
			}
			
			ch = getChar();
		}
		
		String str = getStr(start, cols);
		printInfo(str, "注释", -1);
	}

	/**
	 * @Title: recogDel
	 * @Description: 识别界符的函数
	 * @param: 
	 * @return: void   
	 * @throws
	 */
	private void recogDel(char ch) {
		String str = String.valueOf(ch);

		if (!isEnding()) {
			str += String.valueOf(getChar());
			
			if (isDelimeter(str)) {
				printInfo(str, "双界符", getToken(TokenGenerator.limiterword, str));
			} else {
				cols--;
				printInfo(String.valueOf(ch), "界符", getToken(TokenGenerator.limiterword, String.valueOf(ch)));
			}
		} else {
			printInfo(String.valueOf(ch), "界符", getToken(TokenGenerator.limiterword, String.valueOf(ch)));
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
		System.out.println(name +"  " + type + "   " + token);
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
	
	/**
	 * 活动字符串
	 * @param start
	 * @param end
	 * @return
	 */
	private String getStr(int start, int end) {
		return strBuffer.substring(start, end);
	}
	
	public List<Symbol> getSymbols() {
		return symbols;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		lexer.scanner();
	}
}
