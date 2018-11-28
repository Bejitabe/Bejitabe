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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class DivisionAddRemoveAction  extends AddData implements ActionListener{

	JFrame divisionFrame = new JFrame("役職追加画面");
	JPanel divisionPanel= new JPanel();	
	JLabel labelDName = new JLabel(" 役職名");
	JLabel labelDMemo = new JLabel(" メモ");
	JTextField textDName = new JTextField(15);	
	JTextField textDMemo = new JTextField(15);
    JButton DAddButton = new JButton();
    JButton DBackButton = new JButton();	
	
	DivisionAddRemoveAction(){}
	//役職追加を行うためのフレーム、パネル作成
	public void DivisionFrameSetting(){

		divisionFrame.setResizable(false);
		divisionFrame.setSize(600,150);
		divisionPanel.setLayout(null);
		divisionPanel.setBackground(Color.WHITE);
		
		textDName.setBounds(65,25,160,30);
		textDName.setBorder(new LineBorder(Color.GRAY, 2, true));
		textDName.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textDName.setForeground(Color.WHITE);
		textDName.setBackground(Color.BLACK);
		textDName.setOpaque(true);
		
		
		labelDName.setBounds(10,25,60,30);
		labelDName.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelDName.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelDName.setForeground(Color.WHITE);
		labelDName.setBackground(Color.BLACK);
		labelDName.setOpaque(true);
		
		textDMemo.setBounds(295,25,290,30);
		textDMemo.setBorder(new LineBorder(Color.GRAY, 2, true));
		textDMemo.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textDMemo.setForeground(Color.WHITE);
		textDMemo.setBackground(Color.BLACK);
		textDMemo.setOpaque(true);
		
		
		labelDMemo.setBounds(250,25,50,30);
		labelDMemo.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelDMemo.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelDMemo.setForeground(Color.WHITE);
		labelDMemo.setBackground(Color.BLACK);
		labelDMemo.setOpaque(true);
		
		//各ボタンの設定
		DAddButton.setText("<html><body>役職の追加");
		DAddButton.setBounds(70,80, 160, 25);
		DAddButton.setMargin(new Insets(0,0,0,0));
		DAddButton.setFont(new Font("Arial", Font.PLAIN, 14));
		DAddButton.setActionCommand("add");            
		DAddButton.addActionListener(this);
		
		DBackButton.setText("<html><body>戻る");
		DBackButton.setBounds(320,80, 160, 25);
		DBackButton.setMargin(new Insets(0,0,0,0));
		DBackButton.setFont(new Font("Arial", Font.PLAIN, 16));
		DBackButton.setActionCommand("back");            
		DBackButton.addActionListener(this);
		
		
		divisionPanel.add(textDName);
		divisionPanel.add(labelDName);
		divisionPanel.add(textDMemo);
		divisionPanel.add(labelDMemo);
		divisionPanel.add(DAddButton);
		divisionPanel.add(DBackButton);
		divisionFrame.add(divisionPanel);
		
		divisionFrame.setVisible(true);
	}
	
	public void DivisionAddAction(){
		String sDName =	ActionConData.ConfirmationDataCheckMemo(textDName.getText(),"add");
		if (sDName== null){return;}
	
		String sDMemo =	ActionConData.ConfirmationDataCheckMemo(textDMemo.getText(),"add");
		if (sDMemo== null){return;}
		try { //接続
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
	               
	      String sql =  "alter table usamaruDivision auto_increment = 1;";
	      PreparedStatement st1 = conn.prepareStatement(sql);
	      st1.executeUpdate();
	      st1.close();	      
	       //命令文の設定
	       sql = "insert into usamaruDivision values (?,?,?)";
	       PreparedStatement st2 = conn.prepareStatement(sql);
	      
	       //取得した情報を番号順で書き込み
	       st2.setInt(1,0);
	       st2.setString(2,sDName);
	       st2.setString(3,sDMemo);
	       st2.executeUpdate();
	       //クローズ
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogOK("書込が完了しました！");
	       st2.close();
	       conn.close();
	       DBackAction();
	   } catch (ClassNotFoundException e) {
		   System.out.println("ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogOK("書き込み時にエラーが発生しました。※おそらく社員番号が重複しています。");
		   System.out.println("データベース接続エラー"+ e);
	   } catch (NullPointerException e) {
		   System.out.println("nullがどこかに入っているようです。");
	   }
		
	}
	
	public void DivisionRemoveAction(String number){
		String Number= number;
		try { //接続
		       Class.forName("com.mysql.jdbc.Driver");
		       Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
		       
		       //命令文の設定
		       String sql = "delete from usamaruDivision where DivisionNumber = ? ;";
		       PreparedStatement st = conn.prepareStatement(sql);
		      
		       //取得した情報を番号順で書き込み
		       st.setString(1,Number);
		       st.executeUpdate();
		       //クローズ
		       DialogDisplay dialog = new DialogDisplay();
		       dialog.displayDialogOK("削除が完了しました！");    
		       DivisionDisplayData();
		       st.close();
		       conn.close();
		   } catch (ClassNotFoundException e) {
			   System.out.println("ドライバを読み込めませんでした "+ e);
		   } catch (SQLException e) {
			   System.out.println("データベース接続エラーです。\n"+ e);
		       DialogDisplay dialog = new DialogDisplay();
		       dialog.displayDialogNG("データベース接続エラーです"+e);
		   }
	}
	
	public void DivisionDisplayData(){

		AddDivisionText.setText(ActionConData.DivisionConfirmationDisplay("select * from usamaruDivision order by DivisionNumber ;"));
		//ボタンの生成
		if(Dcounter!=0){
		//正規の文となっている場合はConfirmationクラスで新たにフレームを生成
			ActionConData.setTitle("抽出された管理表 : "+Dcounter+"件の抽出");
			//ラベルをスクロール可能な状態にする。（ボタンの生成）
			ActionConData.setVisible(true);
			displayText.removeAll();
			for(int i = 0 ; i < Dcounter ;i++){
				String j =String.valueOf(i);
				JButton DivisiondeleteButton = new JButton();
				DivisiondeleteButton.setText("<html><body>削除");
	   	 		DivisiondeleteButton.setBounds(380,31+64*i, 90,20 );
	   	 		DivisiondeleteButton.setMargin(new Insets(0,0,0,0));
	   	 		DivisiondeleteButton.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
	   	 		DivisiondeleteButton.setActionCommand("Ddelete"+j);     
	   	 		DivisiondeleteButton.addActionListener(this);
	   	 		//ラベルであるdisplay内に追加
	   	 		AddDivisionText.add(DivisiondeleteButton);
			}
		}else{ 
			DialogDisplay dialog = new DialogDisplay();
			dialog.displayDialogNG("データが存在しないようです。");
			ActionConData.setVisible(false);
		}	
	}

	public void DBackAction(){
		divisionFrame.setVisible(false);
		textDName.setText(null);
		textDMemo.setText(null);
	}
	
	//アクションリスナー
	public void actionPerformed(ActionEvent e) {
		if("back".equals(e.getActionCommand())){
			DBackAction();
		}else if("add".equals(e.getActionCommand())){
			DivisionAddAction();
		    DBackAction();
		}else if(e.getActionCommand().contains("Ddelete")){
			//役職削除ボタンが押された場合
			System.out.println("あああ");
			//作成されたフレーム内の削除ボタンが押された場合の動作
			String sNo = e.getActionCommand();
			sNo = sNo.substring(7);
			int No = Integer.parseInt(sNo);
			//ダイアログメッセージの生成
			DialogDisplay dialog = new DialogDisplay();
			//実行前の確認ダイアログ
			String select[] = {"削除", "キャンセル"};
			if(dialog.displayDialogData(select,"データとして\n役職番号: "+Divisionbackup.get(No)[0]+" , 役職名: "+Divisionbackup.get(No)[1]
					+" , メモ: "+Divisionbackup.get(No)[2]+"\nを削除しますか？")
					==true)	{//trueなら継承されている役職削除追加クラスの削除メソッドへ
					DivisionRemoveAction(Divisionbackup.get(No)[0]);
			}else{
				dialog.displayDialogNG("キャンセルされました。");
			}
		}
	}
}