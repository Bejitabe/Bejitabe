package SQLtest;

import java.util.ArrayList;

public class DisplayDivision {
	/*Add画面にて役職のボックスの表示をDivision内の名前を用いる際に利用するクラス
	 * 
	 *
	 * 
	 */
	DisplayDivision(){
	}
	
	public ArrayList<DivisionQuery>  DivisionAdd(String order){
		ArrayList<DivisionQuery> Dinfos = DivisionQuery.divisionConnectionData(order); 
		
		MyQuery.Dcounter=0;
		//カウンター,バックアップリストを初期化して、動作開始
		/*for(DivisionQuery info : Dinfos){
			MyQuery.Dcounter++;
			}*/
		return Dinfos;
	}
}