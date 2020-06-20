package Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import vo.User;

public class Driver {
	private static String url = "jdbc:sqlserver://localhost:1433;databaseName=banktest";
	private static String user = "sa";
	private static String password = "lhz159hu";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("�������¼�˺ţ�");
		String chrName = sc.next();
		System.out.println("�������¼���룺");
		String upassword = sc.next();
		boolean safety = checkLogin(chrName,upassword);
		int rowCount = 0;
		if(safety!=false) {
			if(chrName.equalsIgnoreCase("admin")) {
				System.out.println("ְԱ����ӭ�㣡");
				int choose=menu();
				while(choose!=3) {
					switch(choose) {
						case 1: 
							rowCount = insertIntoUser();
							break;
						case 2:
							rowCount = deleteFromUser();
							break;
						case 3: 
							System.out.println("��ͻ������˺ţ�");
							chrName = sc.next();
							System.out.println("��ͻ��������룺");
							upassword = sc.next();
							safety = checkLogin(chrName,upassword);
							if(safety!=false) {
								int choose1 = menu1();
								double money;
								while(choose1!=7) {
									switch(choose1) {
									case 1:
										System.out.println("���������");
										money = sc.nextDouble();
										rowCount = updateBalance(chrName,money);
										break;
									case 2: 
										System.out.println("������ȡ���");
										money = sc.nextDouble();
										rowCount = updateBalanceD(chrName,money);
										break;
									case 3: 
										System.out.println("������ת���˻��ͽ�");
										String RChrName = sc.next();
										money = sc.nextDouble();
										rowCount = updateBalanceT(chrName,RChrName,money);
										break;
									case 4:
										System.out.println("����������");
										money = sc.nextDouble();
										rowCount = updateDebt(chrName,money);
										break;
									case 5: 
										System.out.println("�����뻹���");
										money = sc.nextDouble();
										rowCount = updateDebtD(chrName,money);
										break;
									case 6:
										select(chrName);
										break;
									default: 
											System.out.println("����ȷ�����빦��ѡ�");
											break;
									}
									choose1=menu1();
								}
							}
							System.out.println("�˺Ż��������");
					}
					choose=menu();
				}
			}
			System.out.println("���������ͻ���");
			int choose1 = menu1();
			double money;
			while(choose1!=7) {
				switch(choose1) {
				case 1:
					System.out.println("���������");
					money = sc.nextDouble();
					rowCount = updateBalance(chrName,money);
					break;
				case 2: 
					System.out.println("������ȡ���");
					money = sc.nextDouble();
					rowCount = updateBalanceD(chrName,money);
					break;
				case 3: 
					System.out.println("������ת���˻��ͽ�");
					String RChrName = sc.next();
					money = sc.nextDouble();
					rowCount = updateBalanceT(chrName,RChrName,money);
					break;
				case 4:
					System.out.println("����������");
					money = sc.nextDouble();
					rowCount = updateDebt(chrName,money);
					break;
				case 5: 
					System.out.println("�����뻹���");
					money = sc.nextDouble();
					rowCount = updateDebtD(chrName,money);
					break;
				case 6:
					select(chrName);
					break;
				default: 
						System.out.println("���������ȷ����ѡ�");
						break;
				}
				choose1=menu1();
			}
		}
		System.out.println("�˺Ż��������");
		execStoredProc(5);
	}
//		System.out.println("����" + rowCount + "����Ϣ");
			// ��ѯ���� 
	//		System.out.println("�˺���\t\t���\t\t��������\t\t��������");
	//		User user = selectFromtitle(5);
	//		for (Book book2 : books) {
	//			System.out.print(book2.bid);
	//			System.out.print("\t");
	//			System.out.print(book2.title);
	//			System.out.print("\t\t");
	//			System.out.print(book2.price);
	//			System.out.print("\t\t");
	//			System.out.print(book2.inventory);
	//			System.out.print("\t\t");
	//			System.out.print(book2.pub_date);
	//			
	//			System.out.println();
	//		}
	public static boolean checkLogin(String name,String upassword) {
		boolean a = false;
		String sql = "SELECT chrName,upassword "
				+ "FROM user1 "
				+ "WHERE chrName = ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setNString(1, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if(upassword.equals(rs.getNString("upassword"))) {
					a = true;
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	public static int insertIntoUser() {
		int rowCount = 0;
		System.out.println("������Ϣ(��������,�˺���,����)");
		Scanner sc = new Scanner(System.in);
		User u;
		String userName = sc.next();
		String chrName = sc.next();
		String upassword = sc.next();
		u = new User(userName,chrName,upassword);
		String sql = "INSERT INTO user1 (userName,chrName,upassword,balance,debt) "
                + "VALUES (?, ?, ?, ?, ?) ";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setNString(1, u.getUserName());
			pstmt.setNString(2, u.getChrName());
			pstmt.setNString(3, u.getUPassword());
			pstmt.setDouble(4, u.getBalance());
			pstmt.setDouble(5, u.getDebt());
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("�Ѽ���" + rowCount + "����Ϣ");
		sc.close();
		return rowCount;
	}
	public static int deleteFromUser() {
		int rowCount = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("��������Ҫ���ŵ��˻���������");
		String chrName = sc.next();
		String upassword = sc.next();
		String sql = "delete from user1 "
				+ "WHERE chrName = ? and upassword = ? ";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setNString(1, chrName);
			pstmt.setNString(2, upassword);
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("ɾ��" + rowCount +"����Ϣ��");
		sc.close();
		return rowCount;
	}
	public static int updateBalance(String name,double money) {
		int rowCount = 0;
		String sql = "update user1 "
				+ "set balance += ?  where chrName= ? ";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setDouble(1, money);
			pstmt.setNString(2, name);
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("����" + rowCount +"����Ϣ��");
		return rowCount;
	}
	public static int updateBalanceD(String name,double money) {
		int rowCount = 0;
		String sql = "SELECT chrName,balance "
				+ "FROM user1 "
				+ "WHERE chrName = ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setNString(1, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if(money<=rs.getDouble("balance")) {
					sql = "update user1 set balance -= ?  where chrName= ? ";
					Connection con1 = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt1 = con1.prepareStatement(sql);
					pstmt1.setDouble(1, money);
					pstmt1.setNString(2, name);
					rowCount = pstmt1.executeUpdate();
					System.out.println("����" + rowCount +"����Ϣ��");
					return rowCount;
				}
				System.out.println("�������㣬������Ҫ���");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	public static int updateBalanceT(String name,String Name,double money) {
		int rowCount = 0;
		String sql = "SELECT chrName,balance "
				+ "FROM user1 "
				+ "WHERE chrName = ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setNString(1, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if(money<=rs.getDouble("balance")) {
					sql = "update user1 set balance -= ?  where chrName= ? ; update user set balance += ?  where chrName = ?";
					Connection con1 = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt1 = con1.prepareStatement(sql);
					pstmt1.setDouble(1, money);
					pstmt1.setNString(2, name);
					pstmt1.setDouble(3, money);
					pstmt1.setNString(4, Name);
					rowCount = pstmt1.executeUpdate();
					System.out.println("����" + rowCount +"����Ϣ��");
					return rowCount;
				}
				System.out.println("�������㣬������Ҫ���");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	public static int updateDebt(String name,double money) {
		int rowCount = 0;
		String sql = "update user1 "
				+ "set debt += ?  where chrName= ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setDouble(1, money);
			pstmt.setNString(2, name);
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("����" + rowCount +"����Ϣ��");
		return rowCount;
	}
	public static int updateDebtD(String name,double money) {
		int rowCount = 0;
		String sql = "SELECT chrName,debt "
				+ "FROM user1 "
				+ "WHERE chrName = ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setNString(1, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Connection con1 = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt1 = con1.prepareStatement(sql);
				if(money<=rs.getDouble("debt")) {
					sql = "update user1 set debt -= ?  where chrName= ? ";
					
					pstmt1.setDouble(1, money);
					pstmt1.setNString(2, name);
					rowCount = pstmt1.executeUpdate();
					System.out.println("����" + rowCount +"����Ϣ��");
					return rowCount;
				}
				money -= rs.getDouble("debt");
				sql = "update user1 set debt = 0 , balance = ? where chrName= ? ";
				pstmt1.setDouble(1, money);
				pstmt1.setNString(2, name);
				rowCount = pstmt1.executeUpdate();
				System.out.println("����" + rowCount +"����Ϣ,");
				System.out.println("�������󣬴���" + money + "Ԫ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	public static void select(String name) {
		String sql = "SELECT userName,chrName,upassword,balance,debt "
				+ "FROM user1 "
				+ "WHERE chrName = ? ";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setNString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("������\t\t�˺���\t\t����\t\t���\t\t��������\t\t");
				System.out.println(rs.getNString("userName") + "\t\t"+ rs.getNString("chrName") + "\t\t" + rs.getNString("upasswoord") + "\t\t" + rs.getDouble("balance") + "\t\t" + rs.getInt("debt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	public static ArrayList<Book> selectFromBook(double price) {
//		ArrayList<Book> books = new ArrayList<Book>();
//		String sql = "SELECT bid, title, price, inventory, pub_date "
//				+ "FROM book "
//				+ "WHERE price > ? "
//				+ "ORDER BY bid";
//		
//		try (Connection con = DriverManager.getConnection(url, user, password);
//				PreparedStatement pstmt = con.prepareStatement(sql)) {
//			
//			pstmt.setDouble(1, price);
//			ResultSet rs = pstmt.executeQuery();
//			
//			while (rs.next()) {
//				Book book = new Book();
//				book.bid = rs.getNString("bid");
//				book.title = rs.getNString("title");
//				book.price = rs.getDouble("price");
//				book.inventory = rs.getInt("inventory");
//				book.pub_date = rs.getDate("pub_date");
//				
//				books.add(book);				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return books;
//	}
	public static void execStoredProc(double price) {
		String sql = "{? = call total_book(?, ?)}";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				CallableStatement cstmt = con.prepareCall(sql)) {
			// ע�� OUT ����
			cstmt.registerOutParameter(1, JDBCType.INTEGER);
			cstmt.registerOutParameter(3, JDBCType.INTEGER);
			
			cstmt.setDouble(2, price);
			
			cstmt.executeUpdate();
			
			System.out.println("�洢���̵ķ���ֵ��" + cstmt.getInt(1));
			System.out.println("OUT����ֵ��" + cstmt.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int menu() {
		
		int choose=0;
		System.out.println("1.����\n2.����3.��¼\n");
		System.out.println("�����빦��ִ�У�");
		Scanner sc=new Scanner(System.in);
		choose=sc.nextInt();
		if(choose<0||choose>3) {
			System.out.println("��������ȷ���ܴ��룡");
			choose=menu();
		}
		sc.close();
		return choose;
	}
	public static int menu1() {
		
		int choose=0;
		System.out.println("1.���\n2.ȡ��\n3.ת��\n4.����\n5.����\n6.��ѯ\n7.�˳�\n");
		System.out.println("�����빦��ִ�У�");
		Scanner sc=new Scanner(System.in);
		choose=sc.nextInt();
		if(choose<0||choose>7) {
			System.out.println("��������ȷ���ܴ��룡");
			choose=menu1();
		}
		sc.close();
		return choose;
	}
}
