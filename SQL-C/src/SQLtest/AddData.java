package SQLtest;
/*あｄｄデータ　*/
import static SQLtest.APIWindow.*;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class AddData extends ActionData implements ActionListener{
	//ラベルの生成
	JLabel labelNumberNote = new JLabel("<html><body>※社員番号は初期値０で自動生成されます。<br />必要がない限り,値は変更しないようにしてください。");
	JButton DAddButton = new JButton("役職追加");
	JButton DRemoveButton = new JButton("役職削除");	 
	JLabel AddDivisionText = new JLabel();
	//役職画面のスクロール機能
	JScrollPane Divisionscrollpane = new JScrollPane(AddDivisionText);
	
	//コンストラクタ
	AddData(){
		//パネルの追加
		APIWindow.panelAdd.setLayout(null);
		APIWindow.panelAdd.setBackground(Color.WHITE);
		frame.setTitle("うさまる株式会社管理表 : 情報の追加");
		//ラベルの追加
		labelNumberNote.setBounds(30,-10,300,60);
		labelNumberNote.setBorder(new LineBorder(Color.WHITE, 2, true));
		labelNumberNote.setFont(new Font("MSPゴシック", Font.PLAIN, 12));
		labelNumberNote.setForeground(Color.BLACK);
		labelNumberNote.setBackground(Color.WHITE);
		labelNumberNote.setOpaque(true);
		
		//ボタンの設定
		backButton.setText("<html><body>選択画面へ戻る");
		backButton.setActionCommand("back");            
		backButton.addActionListener(this);
		getButton.setText("<html><body>情報の書込");
		getButton.setActionCommand("add");            
		getButton.addActionListener(this);
		
		DAddButton.setVisible(true);
		DAddButton.setBounds(110,120,62,25);
		DAddButton.setMargin(new Insets(0,0,0,0));
		DAddButton.setFont(new Font("Arial", Font.PLAIN, 12));
		DAddButton.setText("<html><body>役職追加");
		DAddButton.setActionCommand("Dadd");            
		DAddButton.addActionListener(this);
		
		DRemoveButton.setVisible(true);
		DRemoveButton.setBounds(167,120,63,25);
		DRemoveButton.setMargin(new Insets(0,0,0,0));
		DRemoveButton.setFont(new Font("Arial", Font.PLAIN, 12));
		DRemoveButton.setText("<html><body>役職削除");
		DRemoveButton.setActionCommand("Dremove");            
		DRemoveButton.addActionListener(this);
		
		Divisionscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Divisionscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		ActionConData.setBounds(505,5, 500, 600);
		ActionConData.setResizable(false);
		//生成された情報をフレームに記載
		ActionConData.add(Divisionscrollpane, BorderLayout.CENTER);
		
		//パネルへ追加
		panelAdd.add(labelNumberNote);
		panelAdd.add(textNumber);
		panelAdd.add(textName);
		panelAdd.add(textOld);
		panelAdd.add(textyService);
		panelAdd.add(scrollMemo);
		panelAdd.add(textDivision);	
		panelAdd.add(labelNumber);
		panelAdd.add(labelName);
		panelAdd.add(labelDivision);
		panelAdd.add(labelOld);
		panelAdd.add(labelyService);
		panelAdd.add(labelMemo);
		panelAdd.add(backButton);
		panelAdd.add(getButton);
		panelAdd.add(DAddButton);
		panelAdd.add(DRemoveButton);
		textDivision.addMouseListener(this);
		
		frame.add(panelAdd);
	}
	
	//情報を書き込む操作
	public void writeDataAction(){
		//役職の入力値を取得するためのクラス生成
		SelectActionListener textDivision = new  SelectActionListener();
		String Division = CheckDivision(Division= textDivision.getText());
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
		
/*		String[] words = sMemo.split("[ ]");
		StringBuilder sb = new StringBuilder();
	        
		for (String w: words){
			sb.append(w+"\n");
		}
		sMemo= sb.toString();*/

		try { //接続
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost/myData?useSSL=false","'beji'","05427dy");
		       
	      String sql =  "alter table usamaruDatabase auto_increment = 1;";
	      PreparedStatement st1 = conn.prepareStatement(sql);
	      st1.executeUpdate();
	      st1.close();	      
	       //命令文の設定
	       sql = "insert into usamaruDatabase values (?,?,?,?,?,?)";
	       PreparedStatement st2 = conn.prepareStatement(sql);
	      
	       //取得した情報を番号順で書き込み
	       st2.setString(1,sNumber);
	       st2.setString(2,sName);
	       st2.setString(3,Division);
	       st2.setString(4,sOld);
	       st2.setString(5,syService);
	       st2.setString(6,sMemo);
	       

	       st2.executeUpdate();
	       //クローズ
	       DialogDisplay dialog = new DialogDisplay();
	       dialog.displayDialogOK("書込が完了しました！");
	       RemoveAction();
	       st2.close();
	       conn.close();
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
	public void DivisionAdd(String Dname){
		textDivision.addItem(Dname);
		textDivision.setSelectedItem(Dname);
	}
	
	
	//アクションリスナー
	public void actionPerformed(ActionEvent e) {
		if("back".equals(e.getActionCommand())){
			//戻るボタンを押したときの動作
			RemoveAction();
		}else if("add".equals(e.getActionCommand())){
			//書き込みボt難を押したときの動作
			writeDataAction();
		}else if("Dadd".equals(e.getActionCommand())){
			textDivision.addItem("未設定");
			textDivision.setSelectedItem("未設定");
			
			DivisionAddRemoveAction Dadd = new DivisionAddRemoveAction();
			Dadd.DivisionFrameSetting();
		}else if("Dremove".equals(e.getActionCommand())){
			DivisionAddRemoveAction RemoveDisplay = new DivisionAddRemoveAction();
			RemoveDisplay. DivisionDisplayData();
	
		}
	}
}