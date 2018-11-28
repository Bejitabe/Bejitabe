package SQLtest;

import javax.swing.JOptionPane;

public class DialogDisplay {

	DialogDisplay(){
	}
	//選択ダイアログの設定
	public boolean displayDialogData(String[] select,String message){
	   	int option = JOptionPane.showOptionDialog(APIWindow.APIConData,message, "確認画面", 
	   			JOptionPane.YES_NO_OPTION,
	   			JOptionPane.QUESTION_MESSAGE,
	   			null, select, select[0]);
		  
		 //選択肢によって何をどうするかの選択     
		if (option == JOptionPane.YES_OPTION){
	   		return true;		     	   	
	   	}else if (option == JOptionPane.NO_OPTION){
		 	return false;
		}
	   	return false;
	}
	//確認ダイアログ
	public void displayDialogNG(String message){
		JOptionPane.showMessageDialog(APIWindow.APIConData, message);
	}
	public void displayDialogOK(String message){
		JOptionPane.showMessageDialog(APIWindow.APIConData, message);
	}
}
