package SQLtest;
/*API*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class APIWindow extends JFrame implements ActionListener {
	//フィールド内の情報
	static JPanel panelMain = new MatiPanel();
	//アドパネルの生成
	static JPanel panelAdd = new MatiPanel();
	//リムーブパネルの生成
	static JPanel panelRemove = new MatiPanel();
	//リネームパネルの生成
	static JPanel panelRename = new MatiPanel();

    static JFrame frame = new JFrame("うさまる株式会社管理表");
    static JButton AddButton = new JButton();
    static JButton RenameButton = new JButton();
    static JButton RemoveButton = new JButton();
    static JButton ConfirmationButton = new JButton();
    static ConfirmationData APIConData  = new ConfirmationData();
    static ConfirmationData divisionData  = new ConfirmationData();
    static  JLabel displayText = new JLabel();
	static  JLabel displayDivisionText = new JLabel();
	JScrollPane scrollpane = new JScrollPane(displayText);
	JScrollPane dscrollpane = new JScrollPane(displayDivisionText);

    //コンストラクタ
	APIWindow(){
	System.out.println("あああああ");
	}

	//初期起動時ののセッティング
	public void APISetting(){
		//画面の設定
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650,360);
		panelMain.setLayout(null);
		panelMain.setBackground(Color.WHITE);
		//各ボタンの設定
		AddButton.setText("<html><body>情報の追加");
		AddButton.setBounds(20,260, 160, 30);
		AddButton.setMargin(new Insets(0,0,0,0));
		AddButton.setFont(new Font("Arial", Font.PLAIN, 16));
		AddButton.setActionCommand("add");
		AddButton.addActionListener(this);

		RenameButton.setText("<html><body>情報の修正");
		RenameButton.setBounds(220,260, 160, 30);
		RenameButton.setMargin(new Insets(0,0,0,0));
		RenameButton.setFont(new Font("Arial", Font.PLAIN, 16));
		RenameButton.setActionCommand("rename");
		RenameButton.addActionListener(this);

		RemoveButton.setText("<html><body>情報の削除");
		RemoveButton.setBounds(420,260, 160, 30);
		RemoveButton.setMargin(new Insets(0,0,0,0));
		RemoveButton.setFont(new Font("Arial", Font.PLAIN, 16));
		RemoveButton.setActionCommand("remove");
		RemoveButton.addActionListener(this);

		ConfirmationButton.setText("<html><body>情報の確認");
		ConfirmationButton.setBounds(450,100, 120,120 );
		ConfirmationButton.setMargin(new Insets(0,0,0,0));
		ConfirmationButton.setFont(new Font("Arial", Font.PLAIN, 16));
		ConfirmationButton.setActionCommand("confirmation");
		ConfirmationButton.addActionListener(this);
		//別ウインドウであるパネルの設定
		displayText.setVerticalAlignment(JLabel.TOP);
		displayText.setHorizontalAlignment(JLabel.RIGHT);

		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		displayDivisionText.setVerticalAlignment(JLabel.TOP);
		displayDivisionText.setHorizontalAlignment(JLabel.RIGHT);

		dscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		dscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//パネルへ各ボタンの追加
		panelMain.add(ConfirmationButton);
		panelMain.add(RemoveButton);
		panelMain.add(RenameButton);
		panelMain.add(AddButton);
		frame.add(panelMain);
		//別ウインドウであるパネルのサイズ、Visible設定
		APIConData.setBounds(5, 5, 500, 600);
		APIConData.setResizable(false);
		//生成された情報をフレームに記載
		APIConData.add(scrollpane, BorderLayout.CENTER);

		divisionData.setBounds(505,5, 500, 600);
		divisionData.setResizable(false);
		//生成された情報をフレームに記載
		divisionData.add(dscrollpane, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	//ボタンの表示、非表示
	public static void ButtonInvisible(){
		AddButton.setVisible(false);
		RenameButton.setVisible(false);
		RemoveButton.setVisible(false);
		ConfirmationButton.setVisible(false);
	}

	public static void ButtonVisible(){
		AddButton.setVisible(true);
		RenameButton.setVisible(true);
		RemoveButton.setVisible(true);
		ConfirmationButton.setVisible(true);
	}

	//ボタンを押したときの動作
	public void actionPerformed(ActionEvent e) {
	System.out.println("はいっていないの＞＞＞");
		if("add".equals(e.getActionCommand())){
			//アドボタンへの移行,設定
			AddData add = new AddData();
			add.textNumber.setText("0");
			ButtonInvisible();
			panelMain.setVisible(false);
			panelAdd.setVisible(true);
		} else if ("rename".equals(e.getActionCommand())){
			//リネーム画面の移行
			RenameData rename = new RenameData();
			rename.textNumber.setText(null);
			ButtonInvisible();
			frame.setTitle("うさまる株式会社管理表");
			panelMain.setVisible(false);
			panelRename.setVisible(true);
		} else if ("remove".equals(e.getActionCommand())){
			//リムーブボタンへの移行
			RemoveData remove = new RemoveData();
			remove.textNumber.setText(null);
			ButtonInvisible();
			panelMain.setVisible(false);
			panelRemove.setVisible(true);
		} else if ("confirmation".equals(e.getActionCommand())){
			//確認画面への移行
			System.out.println("変わっていますか？");
			APIConData.setVisible(true);
			displayText.removeAll();
			displayText.setText(APIConData.ConfirmationDisplay("select * from usamaruDatabase  order by Number ;"));
			APIConData.setTitle("管理表一覧: "+MyQuery.counter+" 件");
			APIConData.add(scrollpane, BorderLayout.CENTER);


			divisionData.setVisible(true);
			displayDivisionText.removeAll();
			displayDivisionText.setText(divisionData.DivisionConfirmationDisplay("select * from usamaruDivision order by DivisionNumber ;"));
			divisionData.setTitle("役職一覧: "+MyQuery.Dcounter+" 件");
			divisionData.add(dscrollpane, BorderLayout.CENTER);
		}
		//再描写
		revalidate();
		repaint();
	}
}
