package gu.pojo;

/**
 * @author gu
 * @date 2019/3/29 14:29
 */
public class InputMessage {

    //<hdfs user,hdfs path of file, hive user, hive database, hive table>.

    String hdfsuser;
    String hdfspath;
    String hiveuser;
    String database;
    String tablename;

    public void setHdfsuser(String hdfsuser) {
        this.hdfsuser = hdfsuser;
    }

    public void setHdfspath(String hdfspath) {
        this.hdfspath = hdfspath;
    }

    public void setHiveuser(String hiveuser) {
        this.hiveuser = hiveuser;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getHdfsuser() {
        return hdfsuser;
    }

    public String getHdfspath() {
        return hdfspath;
    }

    public String getHiveuser() {
        return hiveuser;
    }

    public String getDatabase() {
        return database;
    }

    public String getTablename() {
        return tablename;
    }

    @Override
    public String toString() {
        return "{\"hdfsuser\"=\"" + hdfsuser + "\"," +
                "\"hdfspath\"=\"" + hdfspath + "\"," +
                "\"hiveuser\"=\"" + hiveuser + "\"," +
                "\"database\"=\"" + database + "\"," +
                "\"tablename\"=\"" + tablename + "\"" +
                "}";
    }


}
