package SQLtest;


public class Main {
	//メインクラス
	/* MyQuryクラス：No,Name,Division,Old,yservice,Memoのデータを主に扱うクラス。データを取得した際のバックアップや、
	 * 
	 * Confiramationクラスから取得したデータをスクロールパネルへ表示させるためのつなぎ橋の役目を果たす。
	 * 
	 * APIWindowクラス：主に画面の描写をするためのウインドウの生成、各ボタンの設定や配置を行う。
	 *別画面であるアド画面、リムーブ画面、リネーム画面へもこの画面から遷移することになる。 
	 *
	 * MatiPanelクラス：背景の設定を行うクラス。
	 *
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	public static void main(String[] args) {
		MyQuery  query = new MyQuery();
		APIWindow window = new APIWindow();
		window.APISetting();
	}
}
