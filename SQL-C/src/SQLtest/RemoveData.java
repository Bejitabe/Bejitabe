package SQLtest;
/*リムーブ*/
import static SQLtest.APIWindow.*;
import static SQLtest.MyQuery.*;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;

public class RemoveData extends ActionData implements ActionListener{
	//コンストラクタ
	RemoveData(){
		frame.setTitle("うさまる株式会社管理表 : 情報の削除");
		//パネルセッティング
		APIWindow.panelRemove.setLayout(null);
		textDivision.addItem("未設定");
		textDivision.setSelectedItem("未設定");
		//ボタンの設定
		backButton.setText("<html><body>選択画面へ戻る");
		backButton.setActionCommand("back");            
		backButton.addActionListener(this);
		getButton.setText("<html><body>情報の検索");
		getButton.setActionCommand("get");            
		getButton.addActionListener(this);
		
		//パネルに各コンポーネントを追加
		panelRemove.add(textNumber);
		panelRemove.add(textName);
		panelRemove.add(textDivision);	
		panelRemove.add(textOld);
		panelRemove.add(textyService);
		panelRemove.add(scrollMemo);		
		panelRemove.add(labelNumber);
		panelRemove.add(labelName);
		panelRemove.add(labelDivision);
		panelRemove.add(labelOld);
		panelRemove.add(labelyService);
		panelRemove.add(labelMemo);
		panelRemove.add(backButton);
		panelRemove.add(getButton);
		//APIWindowで生成されているフレームに追加
		textDivision.addMouseListener(this);
		frame.add(panelRemove);
	}
	//削除の実行
	public void DeleteDataAction(String number){
		String Number= number;
		try { //接続
		       Class.forName("com.mysql.jdbc.Driver");
		       Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
		       
		       //命令文の設定
		       String sql = "delete from usamaruDatabase where Number = ? ;";
		       PreparedStatement st = conn.prepareStatement(sql);
		      
		       //取得した情報を番号順で書き込み
		       st.setString(1,Number);
		       st.executeUpdate();
		       //クローズ
		       DialogDisplay dialog = new DialogDisplay();
		       dialog.displayDialogOK("削除が完了しました！");
		       DeleteFindDataAction();
		       st.close();
		       conn.close();
		   } catch (ClassNotFoundException e) {
			   System.out.println("ドライバを読み込めませんでした "+ e);
		   } catch (SQLException e) {
			   System.out.println("データベース接続エラー"+ e);
		       DialogDisplay dialog = new DialogDisplay();
		       dialog.displayDialogOK("データベース接続エラーです"+e);
		   } catch (NullPointerException e) {
			   System.out.println("nullがどこかに入っているようです。");
		   }
	}
	//削除ボタンを押した際のメソッド
	public void DeleteFindDataAction(){
		String sqlValue =FindDataAction();
		//何も追加されていない場合は下のselect文となり、エラーが起こるので動作をしないようにしている.
		if(!(sqlValue.equals("select Number,usamarudatabase.Name,usamaruDivision.Name,Old,yService,usamaruDatabase.Memo from usamaruDatabase Join usamaruDivision on usamarudatabase.division = usamarudivision.DivisionNumber where order by Number ;"))){
		//ＳＱＬにアクセスし適切な情報をラベルテキストに記載
			ActionDisplayText.setText(ActionConData.ConfirmationDisplay(sqlValue));
			if(counter!=0){
				//正規の文となっている場合はConfirmationクラスで新たにフレームを生成
				ActionConData.setTitle("抽出された管理表 : "+counter+"件の抽出");
				ActionConData.setVisible(true);
				//ラベルをスクロール可能な状態にする。（ボタンの生成）
				ActionDisplayText.removeAll();
				for(int i = 0 ; i < counter ;i++){
					String j =String.valueOf(i);
					JButton deleteButton = new JButton();
					deleteButton.setText("<html><body>削除");
			   		deleteButton.setBounds(380,75+112*i, 90,20 );
			   		deleteButton.setMargin(new Insets(0,0,0,0));
			   		deleteButton.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
			   		deleteButton.setActionCommand("delete"+j);     
			   		deleteButton.addActionListener(this);
			   		//ラベルであるdisplay内に追加
			   		ActionDisplayText.add(deleteButton);
				}
			}else{
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("データが存在しないようです。");
				ActionConData.setVisible(false);
			}
		}else{	//無記入の場合
			DialogDisplay dialog = new DialogDisplay();
			dialog.displayDialogNG("何も入力されていません。");
		}
	}
	//ボタンアクション
	public void actionPerformed(ActionEvent e) {
		//戻るボタンが押された場合,パネル内のボタンをすべて非表示、APIwindowを表示
		if("back".equals(e.getActionCommand())){
			//戻るボタンが押された場合の動作
			RemoveAction();
		}else if("get".equals(e.getActionCommand())){
			//検索が押された場合の動作
			DeleteFindDataAction();
		}
		if(e.getActionCommand().contains("delete")){
			//作成されたフレーム内の削除ボタンが押された場合の動作
			String sNo = e.getActionCommand();
			sNo = sNo.substring(6);
			int No = Integer.parseInt(sNo);
			//ダイアログメッセージの生成
			DialogDisplay dialog = new DialogDisplay();
			//実行前の確認ダイアログ
			String select[] = {"削除", "キャンセル"};
		if(dialog.displayDialogData(select,"データとして\nNo: "+backup.get(No)[0]+" , 名前: "+backup.get(No)[1]+" , 役職: "+backup.get(No)[2]
				+" , 年齢: "+backup.get(No)[3]+" , 勤務年数: "+backup.get(No)[4]+"\nメモ:"+backup.get(No)[5]+"\nを削除しますか？")
				==true)	{
			//削除したいNoの取得、実行
			DeleteDataAction(backup.get(No)[0]);
		}else{
			//キャンセルダイアログ
			dialog.displayDialogNG("キャンセルされました。");
			}	
		}
	}
}