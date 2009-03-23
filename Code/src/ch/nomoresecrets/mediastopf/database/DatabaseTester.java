package ch.nomoresecrets.mediastopf.database;

import java.util.List;

import ch.nomoresecrets.mediastopf.database.*;
import ch.nomoresecrets.mediastopf.domain.*;

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
		List<Order> lp = ActiveRecordManager.getObjectList(sql, Order.class);
		for (Order name: lp) System.out.println(name.toString());

		//Get data from DB via adapter class
		lp = DbAdapter.getOrderList();
		for (Order name: lp) System.out.println(name.toString());
		
	}

}
