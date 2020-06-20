package vo;

public class User{
	private String userName;					//用户姓名
	private String chrName;						//用户账号
	private String upassword;					//账号密码
	private double balance;						//余额
	private double debt;						//贷款须还款金额
	
	
	
	public User() {
		super();
	}
	public User(String userName, String chrName, String upassword) {
		this.chrName = chrName;
		this.userName = userName;
		this.upassword = upassword;
		this.balance = 0;
		this.debt = 0;
	}
	public User(String userName, String chrName, String upassword, double balance, double debt) {
		super();
		this.userName = userName;
		this.chrName = chrName;
		this.upassword = upassword;
		this.balance = balance;
		this.debt = debt;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		this.chrName =chrName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUPassword() {
		return upassword;
	}
	public void setUPassword(String upassword) {
		this.upassword = upassword;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getDebt() {
		return debt;
	}
	public void setDebt(double debt) {
		this.debt = debt;
	}
	public String toString() {
		return "User [chrName=" + chrName + ", userName=" + userName + ", upassword=" + upassword + ", balance="
				+ balance + ", debt=" + debt + "]";
	}
}
