package SQLtest;

import java.util.ArrayList;

import javax.swing.JFrame;


public class ConfirmationData extends JFrame{
	//コンストラクタ
	public ConfirmationData(){
	    }
	//ラベルテキストに挿入するための社員一覧表
	public String  ConfirmationDisplay(String order){
		ArrayList<MyQuery> infos = MyQuery.connectionData(order);
		StringBuilder sb = new StringBuilder();
		String listText=null;

		//カウンター,バックアップリストを初期化して、動作開始
		MyQuery.counter=0;
		MyQuery.backup.clear();
		for(MyQuery info:infos){
			MyQuery.counter++;
			String sMemo = info.getMemo();

	        sb.append("<html><body>社員番号 : "+info.getNumber()+"<br />名前 : "+info.getName()+"<br />役職 : "+info.getDivision()
	        + "<br />年齢 : "+info.getOld()+ "<br />勤務年数 : "+info.getyService()+"<br />メモ : "+sMemo
	        +"<br />━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━<br />");

	        //MyQueryのバックアップへ格納
	        MyQuery.BackUp(info.getNumber(),info.getName(),info.getDivision(),info.getOld(),info.getyService(),sMemo);
	        listText = sb.toString();
		}
		return listText;
	}
	//役職一覧表示
	public String  DivisionConfirmationDisplay(String order){
		ArrayList<DivisionQuery> infos = DivisionQuery.divisionConnectionData(order);
		StringBuilder Dsb = new StringBuilder();
		String DlistText=null;

		MyQuery.Dcounter=0;
		MyQuery.Divisionbackup.clear();
		//カウンター,バックアップリストを初期化して、動作開始
		for(DivisionQuery info:infos){
			MyQuery.Dcounter++;
	        Dsb.append("<html><body>役職番号 : "+info.getNumber()+"<br />役職名 : "+info.getDivision()+"<br />メモ : "+info.getMemo()
	        +"<br />━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━<br />");

	        //MyQueryのバックアップへ格納
	        MyQuery.DivisionBackUp(info.getNumber(),info.getDivision(),info.getMemo());
	        DlistText = Dsb.toString();
		}
		return DlistText;
	}
	//社員番号のチェック
	public String ConfirmationDataCheckNumber(String number,String Class){
		try{
			@SuppressWarnings("unused")
			int Number = Integer.parseInt(number);
		}catch(Exception e){
			if(Class.equals("add")){
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("社員番号は数字を入力していないか、何か問題があります。");
			}
			return null;
		}
		return number;
	}
	//年齢のチェック
	public String ConfirmationDataCheckOld(String old,String Class){
		try{
			@SuppressWarnings("unused")
			int Old = Integer.parseInt(old);
		}catch(Exception e){
			if(Class.equals("add")){
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("年齢は数字を入力していないか、何か問題があります。");;
			}
			return null;
		}
		return old;
	}
	//勤務年数のチェック
	public String ConfirmationDataCheckyService(String yService,String Class){
		try{
			@SuppressWarnings("unused")
			int Service = Integer.parseInt(yService);
		}catch(Exception e){
			if(Class.equals("add")){
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("勤務年数は数字を入力していないか、何か問題があります。");
			}
			return null;
		}
		return yService;
	}
	//社員名のチェック
	public String ConfirmationDataCheckName(String name,String Class){
		//情報が未入力かどうか確認
		if(name.equals("")){
			if(Class.equals("add")){
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("名前が空のようです。");
			}
			return null;
		}
		return name;
	}
	//メモのチェック
	public String ConfirmationDataCheckMemo(String memo,String Class){
		//情報が未入力かどうか確認
		if(memo.equals("")){
			if(Class.equals("add")){
				DialogDisplay dialog = new DialogDisplay();
				dialog.displayDialogNG("メモが空のようです。");
			}
			return null;
		}
		return memo;
	}
}
