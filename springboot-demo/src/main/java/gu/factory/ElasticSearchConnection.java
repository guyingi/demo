package gu.factory;

import java.sql.*;

public class ElasticSearchConnection {

    //x-pack-sql-jdbc-6.5.3.jar

    public void test(){
        String driver = "org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver";
        String address = "jdbc:es://143.3.119.71:9200";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(address);
            String sql = "select consumer.id,count(distinct service.id.keyword ) from \"logstash-2019.*\" where started_at>1547434613675 and started_at<1547437700119 group by consumer.id";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                String service = results.getString(1);
                String count = results.getString(2);
                System.out.println(service+count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
