package SQLtest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MyQuery {
	//６つの基本データのフィールド
	private String number;
	private String name;
	private String division;
	private String old;
	private String yService;
	private String memo;
    //MyQuery内のリストを格納するリスト
	static ArrayList<String[]> backup = new ArrayList<String[]>();
	static ArrayList<String[]> Divisionbackup = new ArrayList<String[]>();
    static int counter =0;		//抽出したテーブルの数をカウントする変数
    static int Dcounter =0;		//抽出したテーブルの数をカウントする変数

	MyQuery(String number,String name, String division,String old,String yService,String memo){
		this.number= number;
		this.name= name;
		this.division = division;
		this.old = old;
		this.yService = yService;
		this.memo= memo;
	}

	MyQuery(){
		this.number= null;
		this.name= null;
		this.division = null;
		this.old = null;
		this.yService = null;
		this.memo= null;
	}

	//一次的なバックアップメソッド。主にConfirmationnクラスからアクセスを行い、取得を行う。
	public static void BackUp(String number,String name,String Division,String old,String yService,String memo){
		String[] backupData = {number,name,Division,old,yService,memo};
		backup.add(backupData);
	}
	//一次的な役職データベースのバックアップメソッド。主にConfirmationnクラスからアクセスを行い、取得を行う。
	public static void DivisionBackUp(String number,String name,String memo){
		String[] backupData = {number,name,memo};
		Divisionbackup.add(backupData);
	}
	//データベースへアクセスし、命令：orderによってデータの取得を行うメソッド。
	public static ArrayList<MyQuery> connectionData(String order){
	try {
			//MYSQLの呼び出し
	       Class.forName("com.mysql.jdbc.Driver");
	       Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost/myData?allowPublicKeyRetrieval=true&useSSL=false","'beji'","05427dy");
	       //接続、取得した情報の一次格納
	       Statement st = conn.createStatement();
	       ResultSet rs =
	       st.executeQuery(order);	//ここで受け取った命令の格納を行い、次の行で命令の実行を行う。
	       //リストの呼び出し
	       ArrayList<MyQuery> infos = new ArrayList<MyQuery>();
	       //命令の実行
	       while(rs.next()) {
	    	   //引数のないコンストラクタの呼び出し、情報の取得
	    	   MyQuery info = new MyQuery();
	           info.number = rs.getString(1);
	           info.name = rs.getString(2);
	           info.division = rs.getString(3);
	           info.old = rs.getString(4);
	           info.yService = rs.getString(5);
	           info.memo = rs.getString(6);
	           infos.add(info);
	       }
	       	//終了、データベースとの切断
	       rs.close();
	       st.close();
	       conn.close();
	       //取得した情報を必要とするクラスへ受け渡し
	       return infos;
		} catch (ClassNotFoundException e) {
			System.out.println("ドライバを読み込めませんでした "+ e);
		} catch (SQLException e) {
			System.out.println("データベース接続エラー"+ e);
		}
	return null;
	}

	//getter,setterの作成
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getOld() {
		return old;
	}
	public void setOld(String old) {
		this.old = old;
	}
	public String getyService() {
		return yService;
	}
	public void setyService(String yService) {
		this.yService = yService;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}