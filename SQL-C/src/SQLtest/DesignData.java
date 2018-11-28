package SQLtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class DesignData implements MouseListener{
	//共通のデータ
    ConfirmationData ActionConData  = new ConfirmationData();
    
	JLabel labelNumber = new JLabel("   社員番号");
	JLabel labelName = new JLabel("   名前");
	JLabel labelDivision = new JLabel(" 役職");
	JLabel labelOld = new JLabel("  年齢");
	JLabel labelyService = new JLabel("  勤務年数");
	JLabel labelMemo = new JLabel("    メモ");
	JLabel ActionDisplayText = new JLabel();
	
	//テキストエリア、フィールドの生成
	JTextField textNumber = new JTextField(3);
	JTextField textName = new JTextField(15);	
	JTextField textOld = new JTextField(3);
	JTextField textyService = new JTextField(3);
	JTextArea textMemo = new JTextArea();

	JComboBox<String> textDivision;
	JScrollPane scrollMemo = new JScrollPane(textMemo);
	JScrollPane scrollActionText = new JScrollPane(ActionDisplayText);

	DisplayDivision  addDisplay = new  DisplayDivision();
	//ボタンの生成
	JButton backButton = new JButton();
	JButton getButton = new JButton();
	
	DesignData(){
		//AddSetting();
		DisplayDivision  addDisplay = new  DisplayDivision();

		//テキストエリア、フィールドの追加
		textNumber.setBounds(120,50,110,30);
		textNumber.setBorder(new LineBorder(Color.GRAY, 2, true));
		textNumber.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textNumber.setForeground(Color.WHITE);
		textNumber.setBackground(Color.BLACK);
		textNumber.setOpaque(true);	

		textName.setBounds(310,50,270,30);
		textName.setBorder(new LineBorder(Color.GRAY, 2, true));
		textName.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textName.setForeground(Color.WHITE);
		textName.setBackground(Color.BLACK);
		textName.setOpaque(true);
		
		textDivision = new JComboBox<>();
		textDivision.addActionListener( new SelectActionListener() );
		textDivision.setBounds(75,90,155,30);
		textDivision.setBorder(new LineBorder(Color.GRAY, 2, true));
		textDivision.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textDivision.setForeground(Color.WHITE);
		textDivision.setBackground(Color.BLACK);
		textDivision.setOpaque(true);		

		ArrayList<DivisionQuery> DisplayDivision = addDisplay. DivisionAdd("select * from usamaruDivision order by DivisionNumber ;");
			for(DivisionQuery info: DisplayDivision){
				textDivision.addItem(info.getDivision());
		}
			
		//テキストの位置設定
		textOld.setBounds(300,90,90,30);
		textOld.setBorder(new LineBorder(Color.GRAY, 2, true));
		textOld.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textOld.setForeground(Color.WHITE);
		textOld.setBackground(Color.BLACK);
		textOld.setOpaque(true);
		
		textyService.setBounds(480,90,100,30);
		textyService.setBorder(new LineBorder(Color.GRAY, 2, true));
		textyService.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textyService.setForeground(Color.WHITE);
		textyService.setBackground(Color.BLACK);
		textyService.setOpaque(true);
	
		textMemo.setBorder(new LineBorder(Color.GRAY, 2, true));
		textMemo.setFont(new Font("MSPゴシック", Font.PLAIN, 18));
		textMemo.setForeground(Color.WHITE);
		textMemo.setBackground(Color.BLACK);
		textMemo.setOpaque(true);
		textMemo.setLineWrap(true);	
		
		//スクロールの追加
		scrollMemo.setBounds(30,160,550,100);	
		scrollMemo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMemo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		
		//ラベルの位置設定
		labelNumber.setBounds(30,50,100,30);
		labelNumber.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelNumber.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelNumber.setForeground(Color.WHITE);
		labelNumber.setBackground(Color.BLACK);
		labelNumber.setOpaque(true);
			
		labelName.setBounds(250,50,60,30);
		labelName.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelName.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelName.setForeground(Color.WHITE);
		labelName.setBackground(Color.BLACK);
		labelName.setOpaque(true);
	
		labelDivision.setBounds(30,90,50,30);
		labelDivision.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelDivision.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelDivision.setForeground(Color.WHITE);
		labelDivision.setBackground(Color.BLACK);
		labelDivision.setOpaque(true);
		
		labelOld.setBounds(250,90,50,30);
		labelOld.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelOld.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelOld.setForeground(Color.WHITE);
		labelOld.setBackground(Color.BLACK);
		labelOld.setOpaque(true);
		
		labelyService.setBounds(400,90,80,30);
		labelyService.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelyService.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelyService.setForeground(Color.WHITE);
		labelyService.setBackground(Color.BLACK);
		labelyService.setOpaque(true);
			
		labelMemo.setBounds(30,130,70,30);
		labelMemo.setBorder(new LineBorder(Color.GRAY, 2, true));
		labelMemo.setFont(new Font("MSPゴシック", Font.PLAIN, 16));
		labelMemo.setForeground(Color.WHITE);
		labelMemo.setBackground(Color.BLACK);
		labelMemo.setOpaque(true);
		
		//別ウインドウであるパネルの設定
		ActionDisplayText.setVerticalAlignment(JLabel.TOP);
		ActionDisplayText.setHorizontalAlignment(JLabel.RIGHT);
		
		scrollActionText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollActionText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		ActionConData.setBounds(5, 5, 500, 600);
		ActionConData.setResizable(false);
		//生成された情報をフレームに記載
		ActionConData.add(scrollActionText, BorderLayout.CENTER);
		
		backButton.setVisible(true);
		backButton.setBounds(100,270, 160,30 );		
		backButton.setMargin(new Insets(0,0,0,0));
		backButton.setFont(new Font("Arial", Font.PLAIN, 16));
		
		getButton.setVisible(true);
		getButton.setBounds(300,270, 160,30 );
		getButton.setMargin(new Insets(0,0,0,0));
		getButton.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) {

	}
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		String text =  textDivision.getItemAt(textDivision.getSelectedIndex());
		textDivision.removeAllItems();
		ArrayList<DivisionQuery> DisplayDivision = addDisplay. DivisionAdd("select * from usamaruDivision order by DivisionNumber ;");
		for(DivisionQuery info: DisplayDivision){
			textDivision.addItem(info.getDivision());
		}
		textDivision.addItem("未設定");
		textDivision.setSelectedItem(text);
		
	}
	public void mouseExited(MouseEvent e) {}
	
	
	class SelectActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = textDivision.getItemAt(textDivision.getSelectedIndex());	
		}
	
		public String getText(){
			String s = textDivision.getItemAt(textDivision.getSelectedIndex());
		return s;
		}
	}
}


