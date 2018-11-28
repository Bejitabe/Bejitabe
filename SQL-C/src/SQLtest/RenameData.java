package SQLtest;

import static SQLtest.APIWindow.*;
import static SQLtest.MyQuery.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;



public class RenameData extends ActionData implements ActionListener{
	//特殊な変数
	Integer BackupNo = 0;
	SelectActionListener BoxSelectDivision = new  SelectActionListener();	
	//コンストラクタ
	RenameData(){
		//パネルセッティング
		APIWindow.panelRename.setLayout(null);
		frame.setTitle("うさまる株式会社管理表 : 情報の編集");
		panelRename.setBackground(Color.WHITE);	
		textDivision.addItem("未設定");
		textDivision.setSelectedItem("未設定");
		//戻るボタンの設定
		backButton.setText("<html><body>選択画面へ戻る");
		backButton.setActionCommand("back");            
		backButton.addActionListener(this);
		//検索ボタンの設定
		getButton.setText("<html><body>情報の検索");
		getButton.setActionCommand("get");            
		getButton.addActionListener(this);
		//パネルに各コンポーネントを追加
		panelRename.add(textNumber);
		panelRename.add(textName);
		panelRename.add(textDivision);	
		panelRename.add(textOld);
		panelRename.add(textyService);
		panelRename.add(scrollMemo);
		panelRename.add(labelNumber);
		panelRename.add(labelName);
		panelRename.add(labelDivision);
		panelRename.add(labelOld);
		panelRename.add(labelyService);
		panelRename.add(labelMemo);
		panelRename.add(backButton);
		panelRename.add(getButton);
		//APIWindowで生成されているフレームに追加
		textDivision.addMouseListener(this);
		frame.add(panelRename);
	}
	
	//情報を書き込む操作
	public void RewriteDataAction(){
		//役職の入力値を取得するためのクラス生成
		String sDivision = CheckDivision(BoxSelectDivision.getText());
		String bDivision =  CheckDivision(backup.get(BackupNo)[2]);
		//ConfirmationDataクラスでデータのチェック
		String sNumber =	ActionConData.ConfirmationDataCheckNumber(textNumber.getText(),"add");
		if (sNumber== null){return;}
		String sOld =	ActionConData.ConfirmationDataCheckOld(textOld.getText(),"add");
		if (sOld== null){return;}
		String syService =	ActionConData.ConfirmationDataCheckyService(textyService.getText(),"add");
		if (syService== null){return;}
		String sName =	ActionConData.ConfirmationDataCheckName(textName.getText(),"add");
		if (sName== null){return;}
		String sMemo =	ActionConData.ConfirmationDataCheckMemo(textMemo.getText(),"add");
		if (sMemo== null){return;}
 	
	try {//接続
	       Class.forName("com.mysql.jdbc.Driver");
	       Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
       
       //命令文の設定
       String sql = " UPDATE usamaruDatabase SET Number = ?,Name = ?,Division = ? "
	      		+ ",Old = ?,Yservice = ?,Memo = ? where Number = ? and Name = ?"
	       		+ " and Division = ? and Old = ? and Yservice = ? and Memo = ?;";
	       PreparedStatement st = conn.prepareStatement(sql);
	       st.setString(1,sNumber);
	       st.setString(2,sName);
	       st.setString(3,sDivision);
	       st.setString(4,sOld);
	       st.setString(5,syService);
	       st.setString(6,sMemo);
	       //取得した情報を番号順で書き込み
	       st.setString(7,backup.get(BackupNo)[0]);
	       st.setString(8,backup.get(BackupNo)[1]);
	       st.setString(9,bDivision);
	       st.setString(10,backup.get(BackupNo)[3]);
	       st.setString(11,backup.get(BackupNo)[4]);
	       st.setString(12,backup.get(BackupNo)[5]);
	       st.executeUpdate(); 
	       //クローズ
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogOK("上書きが完了しました！");
			RenameRemoveAction();
	       st.close();
	       conn.close();
	   } catch (ClassNotFoundException e) {
		   System.out.println("ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogNG("エラーが発生しました。おそらく社員番号が重複しています。");
		   System.out.println("データベース接続エラー"+ e);
	   } catch (NullPointerException e) {
		   System.out.println("nullがどこかに入っているようです。");
	   }
	}
	//リネームクラスのデータ検索実行メソッド
	public void RenameFindDataAction(){
		String sqlValue =FindDataAction();
		//何も追加されていない場合は下のselect文となり、エラーが起こるので動作をしないようにしている.
		if(!(sqlValue.equals("select Number,usamarudatabase.Name,usamaruDivision.Name,Old,yService,usamaruDatabase.Memo from usamaruDatabase Join usamaruDivision on usamarudatabase.division = usamarudivision.DivisionNumber where order by Number ;"))){
		//ＳＱＬにアクセスし適切な情報をラベルテキストに記載
			ActionDisplayText.setText(ActionConData.ConfirmationDisplay(sqlValue));
			if(MyQuery.counter!=0){
				//正規の文となっている場合はConfirmationクラスで新たにフレームを生成
				ActionConData.setTitle("抽出された管理表 : "+MyQuery.counter+"件の抽出");
				ActionConData.setVisible(true);
				//ラベルをスクロール可能な状態にする。（ボタンの生成）
				ActionDisplayText.removeAll();
				//編集ボタンの生成
				for(int i = 0 ; i < MyQuery.counter ;i++){
					String j =String.valueOf(i);
					JButton EditButton = new JButton();
					EditButton.setText("<html><body>編集");
					EditButton.setBounds(380,75+112*i, 90,20 );
					EditButton.setMargin(new Insets(0,0,0,0));
					EditButton.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
					EditButton.setActionCommand("rename"+j);     
					EditButton.addActionListener(this);
			   		//ラベルであるdisplay内に追加
			   		ActionDisplayText.add(EditButton);
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
	//バックアップ内のデータの取得メソッド
	public void RenameDataGet(String number,String name,String division,String old, String yService,String memo){
		//テキスト内の更新
		textNumber.setText(number);
		textName.setText(name);
		textDivision.setSelectedItem(division);
		textyService.setText(yService);
		textOld.setText(old);
		textMemo.setText(memo);
		//ダイアログ表示
	    DialogDisplay dialog = new DialogDisplay();
	    dialog.displayDialogOK("編集画面へ移行します！");
	    //ページ内と内部の更新
	    textDivision.removeItem("未設定");
	    getButton.setText("<html><body>情報の上書き");
		getButton.setActionCommand("Rewrite"); 
		//別ウインドウ非表示
		ActionConData.setVisible(false);
	}
	//リネームクラスの特殊なリムーブアクション
	public void RenameRemoveAction(){
		//戻るボタンが押された場合の動作
		if(!(textDivision.getSelectedItem().equals("未設定"))){
			textDivision.addItem("未設定");	
		}
		
		textDivision.setSelectedItem("未設定");
		textNumber.setText(null);
		getButton.setText("<html><body>情報の検索");
		getButton.setActionCommand("get"); 
		textNumber.setEditable(true);
		RemoveAction();
	}
	//ボタンアクション
	public void actionPerformed(ActionEvent e) {
		//戻るボタンが押された場合,パネル内のボタンをすべて非表示、APIwindowを表示
		if("back".equals(e.getActionCommand())){
			RenameRemoveAction();
		}else	if("get".equals(e.getActionCommand())){
			//検索が押された場合の動作
			RenameFindDataAction();
		}else if(e.getActionCommand().contains("rename")){
			//作成されたフレーム内の削除ボタンが押された場合の動作（押されたボタンの確認、
			//ステータス変更とバックアップの呼び出し準備
			String sNo = e.getActionCommand();
			sNo = sNo.substring(6);
			System.out.println(sNo);
			int No = Integer.parseInt(sNo);
			BackupNo = No;
			//ダイアログメッセージのインスタンス生成
			DialogDisplay dialog = new DialogDisplay();
			//確認ダイアログの生成
			String select[] = {"編集", "キャンセル"};
			
			if(dialog.displayDialogData(select,"データとして\n社員番号: "+backup.get(No)[0]+" , 名前: "+backup.get(No)[1]
					+" , 役職: "+backup.get(No)[2]+" , 年齢: "+backup.get(No)[3]+" , 勤務年数: "+backup.get(No)[4]
					+"\nメモ:"+backup.get(No)[5]+"\nを編集しますか？")==true){
				//編集するための情報をセット、trueなら以下の上書き画面へ遷移
				textNumber.setEditable(false);
				RenameDataGet(backup.get(No)[0],backup.get(No)[1],backup.get(No)[2]
					,backup.get(No)[3],backup.get(No)[4],backup.get(No)[5]);
			}else{//キャンセルの場合はキャンセルダイアログの生成
				dialog.displayDialogNG("キャンセルされました。");
			}
		}else if(e.getActionCommand().contains("Rewrite")){
			//ダイアログメッセージの生成
			DialogDisplay dialog = new DialogDisplay();
			String select[] = {"上書きの確定", "キャンセル"};
			//上書き確認ダイアログの生成
			if(dialog.displayDialogData(select,"編集前のデータ\n社員番号: "+backup.get(BackupNo)[0]+" , 名前: "+backup.get(BackupNo)[1]
					+" , 役職: "+backup.get(BackupNo)[2]+" , 年齢: "+backup.get(BackupNo)[3]+" , 勤務年数: "
					+backup.get(BackupNo)[4]+"\nメモ:"+backup.get(BackupNo)[5]+"\nを、下記のデータ\n"
					+" , 名前: "+textName.getText()+" , 役職: "+BoxSelectDivision.getText()+" , 年齢: "+textOld.getText()+" , 勤務年数: "
					+textyService.getText()+"\nメモ:"+textMemo.getText()+"\nへ上書きしてもよろしいですか？")==true)	{
				RewriteDataAction();	
			}else{
				dialog.displayDialogNG("キャンセルされました。");
			}
		}
	}
}
