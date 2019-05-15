package gu.entity;//package report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="anjiantongji")
public class Anjiantongji {

    @Id
    private Long id;

    @Column
    private String keshi;

    @Column
    private String xiangmu;

    @Column
    private String yuefen;

    @Column
    private Long anjian_count = 0L;

    @Column
    private Long person_count = 0L;

    public void setId(Long id) {
        this.id = id;
    }

    public void setKeshi(String keshi) {
        this.keshi = keshi;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public void setYuefen(String yuefen) {
        this.yuefen = yuefen;
    }

    public void setAnjian_count(Long anjian_count) {
        this.anjian_count = anjian_count;
    }

    public void setPerson_count(Long person_count) {
        this.person_count = person_count;
    }

    public Long getId() {
        return id;
    }

    public String getKeshi() {
        return keshi;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public String getYuefen() {
        return yuefen;
    }

    public Long getAnjian_count() {
        return anjian_count;
    }

    public Long getPerson_count() {
        return person_count;
    }
}
