package edu.wctc.dj.week9.namesapp9.data;

import edu.wctc.dj.week9.namesapp9.model.Address;
import edu.wctc.dj.week9.namesapp9.model.Name;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddressDao {
		
	public Address getAddress(Name name) throws Exception {
		Address address = null;

		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
				"select * from address where nameid = ?"
			);
			stmt.setString(1, name.getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				address = new Address(street, city, state, zip);
			}
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (Exception e) {}
				try { stmt.close(); } catch (Exception e) {}
				try { conn.close(); } catch (Exception e) {}
			}
		}

		return address;
	}
	
}
