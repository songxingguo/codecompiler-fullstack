package dao;
/**  
* @Title: Token.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午5:01:44
*/


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.07
 */
public class Token {
	String word;
	
	int token;

	public Token(String word, int token) {
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

	@Override
	public String toString() {
		return "Token [word=" + word + ", token=" + token + "]";
	}
}
