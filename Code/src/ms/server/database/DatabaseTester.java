package ms.server.database;

import java.util.List;

import ms.server.database.*;
import ms.server.domain.*;



public class DatabaseTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loadData();

	}
	
	private static void loadData() {
		//Get data from DB direct via domain object and activerecord
		String sql = "select * from Auftrag";
		List<Auftrag> lp = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		for (Auftrag name: lp) System.out.println(name.toString());

		
		//Get data from DB via adapter class
		lp = DbAdapter.getOrderList();
		for (Auftrag name: lp) System.out.println(name.toString());
		
		
		
	}

}