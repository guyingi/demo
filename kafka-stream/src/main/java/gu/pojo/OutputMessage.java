package gu.pojo;

/**
 * @author gu
 * @date 2019/4/1 18:27
 */
public class OutputMessage {

    String database;
    String tablename;
    String wenshuid;
    String text;

    public String getDatabase() {
        return database;
    }

    public String getTablename() {
        return tablename;
    }

    public String getWenshuid() {
        return wenshuid;
    }

    public String getText() {
        return text;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setWenshuid(String wenshuid) {
        this.wenshuid = wenshuid;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{\"database\":\"" + database + "\"," +
                "\"tablename\":\"" + tablename + "\"," +
                "\"wenshuid\":\"" + wenshuid + "\"," +
                "\"text\":\"" + text +"\""+
                "}";
    }
}
