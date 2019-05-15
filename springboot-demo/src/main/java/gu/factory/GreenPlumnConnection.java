package gu.factory;

import java.sql.*;

public class GreenPlumnConnection {

    public void test() throws ClassNotFoundException, SQLException {
        // URL
        String url = "jdbc:pivotal:greenplum://143.3.119.82:5432;DatabaseName=wenshu";

        // 数据库用户名
        String username = "gpadmin ";

        // 数据库密码
        String password = "gpadmin";

        // 加载驱动
        Class.forName("com.pivotal.jdbc.GreenplumDriver");

        // 获取连接
        Connection conn = DriverManager.getConnection(url, username, password);

        String sql = "select * from testTable";
        PreparedStatement pre = conn.prepareStatement(sql);
        ResultSet rs = pre.executeQuery();

        while(rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

}
