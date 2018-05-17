package edu.wctc.dj.week9.namesapp9.data;

import edu.wctc.dj.week9.namesapp9.model.Name;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NameDao {
	
	public List<Name> getNames() throws Exception {
		List<Name> nameList = new ArrayList<>();

		Connection conn = ConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select * from name");
			rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String first = rs.getString("firstname");
				String last = rs.getString("lastname");
				nameList.add(new Name(id, first, last));
			}
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (Exception e) {}
				try { stmt.close(); } catch (Exception e) {}
				try { conn.close(); } catch (Exception e) {}
			}
		}

		return nameList;
	}

}
