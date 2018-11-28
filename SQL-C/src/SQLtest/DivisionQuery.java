package SQLtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DivisionQuery {
	private String Number= null;
	private String Division= null;
	private String Memo = null;

	DivisionQuery(){
		this.Number= null;
		this.Division= null;
		this.Memo = null;
	}
	//データベースへアクセスし、命令：orderによってデータの取得を行うメソッド。
	public static ArrayList<DivisionQuery> divisionConnectionData(String order){
		try {//MYSQLの呼び出し
	       Class.forName("com.mysql.jdbc.Driver");
	       Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost/myData?allowPublicKeyRetrieval=true&useSSL=false","'beji'","05427dy");
	       //接続、取得した情報の一次格納
	       Statement st = conn.createStatement();
	       ResultSet rs =
	       st.executeQuery(order);	//ここで受け取った命令の格納を行い、次の行で命令の実行を行う。
	       //リストの呼び出し
	       ArrayList<DivisionQuery> infos = new ArrayList<DivisionQuery>();
	       //命令の実行
	       while(rs.next()) {
	    	   //引数のないコンストラクタの呼び出し、情報の取得
	    	   DivisionQuery info = new DivisionQuery();
	           info.Number = rs.getString(1);
	           info.Division = rs.getString(2);
	           info.Memo = rs.getString(3);
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
	//ゲッター、セッターの設定
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getDivision() {
		return Division;
	}
	public void setDivision(String division) {
		Division = division;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
}


