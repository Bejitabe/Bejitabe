package SQLtest;

import static SQLtest.APIWindow.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionData extends DesignData{
	
	ActionData(){}
	//検索ボタンを押したときの動作
	public String FindDataAction(){
		//役職の入力値を取得するためのクラス
		//検索を行う際の文字列追加ソース。初期設定としてselect文を記載
		StringBuilder sb = new StringBuilder();
		String sqlValue=null;
		sb.append("select Number,usamarudatabase.Name,usamaruDivision.Name,Old,yService,usamaruDatabase.Memo from usamaruDatabase Join usamaruDivision on usamarudatabase.division = usamarudivision.DivisionNumber where ");
		
		//社員番号のチェック
		String sNumber =	ActionConData.ConfirmationDataCheckNumber(textNumber.getText(),"remove");
		if (sNumber!= null){sb.append("usamarudatabase.Number like \'%" +sNumber+"%' ");}
		//社員名のチェック
		String sName =	ActionConData.ConfirmationDataCheckName(textName.getText(),"remove");
		if(sName!= null){
			if(sNumber!=null){sb.append(" and ");}
			sb.append("usamarudatabase.Name like \'%" +sName+"%' ");
		}	
		//年齢のチェック
		String sOld =	ActionConData.ConfirmationDataCheckOld(textOld.getText(),"remove");
		if(sOld!= null){
			if(sNumber!=null||sName != null){sb.append(" and ");}
			sb.append("usamarudatabase.Old like \'%" +sOld+"%' ");
		}	
		//勤務年数のチェック
		String syService =ActionConData.ConfirmationDataCheckyService(textyService.getText(),"remove");
		if(syService!= null){
			if(sNumber!=null||sName != null ||sOld != null){sb.append(" and ");}
			sb.append("usamarudatabase.yService like \'%" +syService+"%' ");
		}	
		//メモのチェック
		String sMemo= ActionConData.ConfirmationDataCheckMemo(textMemo.getText(),"remove");
		if(sMemo!= null){
			if(sNumber!=null||sName != null ||sOld != null||syService != null){sb.append(" and ");}
			sb.append("usamarudatabase.Memo like \'%" +sMemo+"%' ");
		}	
			
		SelectActionListener textDivision = new  SelectActionListener();
		String sDivision = textDivision.getText();	//取得、未設定以外であれば構文の追加
		if(!(sDivision.equals("未設定"))){
			if(sNumber!=null||sName != null ||sOld != null||syService != null){sb.append(" and ");}
			sb.append("usamaruDivision.name like \'" +sDivision+"\' ");
		}
		
		//６つのカラムチェックを終えると、;を追加してＳＱＬに接続する
		sb.append("order by Number ;");
		sqlValue = sb.toString();
		//情報を必要とするクラスへ
		return sqlValue;
	}
	//戻るボタンを押したときの動作
	public void RemoveAction(){	
		frame.setTitle("うさまる株式会社管理表");
		textName.setText(null);
		textOld.setText(null);
		textyService.setText(null);
		textMemo.setText(null);
		panelAdd.setVisible(false);
		panelRemove.setVisible(false);
		panelRename.setVisible(false);
		APIWindow.panelMain.setVisible(true);
		APIWindow.ButtonVisible();
	}
	//役職のチェック(役職名の文字列（主キー）から数列（外部キー）への変換メソッド)
	public String CheckDivision(String order){
		//初期メッセージの生成
		String message=("select DivisionNumber from usamaruDivision where Name = \'"+order+"\' ;");
		String result= null;
		try { //接続
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
		  //命令文の設定
	      PreparedStatement st = conn.prepareStatement(message);
	      ResultSet rs = st.executeQuery();
	      //データベース内の情報取得
	      while(rs.next()){result= rs.getString("DivisionNumber");}
	      //取得後、データベースと切断、リターンで必要とするクラスへ
	      st.close();
	      rs.close();
	      conn.close();
	      return result;
	   } catch (ClassNotFoundException e) {
		   System.out.println("ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogOK("読み込み時にエラーが発生しました。");
		   System.out.println("データベース接続エラー"+ e);
	   } catch (NullPointerException e) {
	   }
	   return null;
	}

}
