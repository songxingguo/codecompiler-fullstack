/**  
* @Title: SymbolTable.java
* @Package lexicalanalysis
* @Description: TODO
* @author songxingguo
* @date 2018年4月7日 下午5:12:37
*/
package lexicalanalysis;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author songxinggo
 * @date 2018.04.07
 */
public class Symbol {

	private String name;
	
	private int token;
	
	private String type;
	
	private String kind;
	
	private String val;
	
	private String addr;
	
	public Symbol(String name) {
		this.name = name;
	}
	
	public Symbol(String name, int token) {
		this.name = name;
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
