package lexicalanalysis;
/**  
* @Title: Entry.java
* @Package 
* @Description: TODO
* @author songxingguo
* @date 2018年4月12日 下午3:22:59
*/

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.12
 */
public class Entry {
	private String word;
	
	private int token;
	
	Entry(String word, int token) {
		this.word = word;
		this.token = token;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
}
