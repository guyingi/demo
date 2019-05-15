package gu.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Conf {

    @Value("${machine.id}")
    String machineid;

    @Value("${dir.origin}")
    String origindir;

    @Value("${dir.temp}")
    String tempdir;

    @Value("${dir.result}")
    String resultdir;

    @Value("${db.url}")
    String dburl;

    @Value("${db.user}")
    String dbuser;

    @Value("${db.password}")
    String dbpassword;

    public String getMachineid() {
        return machineid;
    }

    public String getOrigindir() {
        return origindir;
    }

    public String getTempdir() {
        return tempdir;
    }

    public String getResultdir() {
        return resultdir;
    }

    public String getDburl() {
        return dburl;
    }

    public String getDbuser() {
        return dbuser;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    @Override
    public String toString() {
        return "ConfBean{" +
                "machineid='" + machineid + '\'' +
                ", origindir='" + origindir + '\'' +
                ", tempdir='" + tempdir + '\'' +
                ", resultdir='" + resultdir + '\'' +
                ", dburl='" + dburl + '\'' +
                ", dbuser='" + dbuser + '\'' +
                ", dbpassword='" + dbpassword + '\'' +
                '}';
    }
}
