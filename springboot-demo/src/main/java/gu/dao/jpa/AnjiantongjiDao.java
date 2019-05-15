package gu.dao.jpa;

import gu.entity.Anjiantongji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface AnjiantongjiDao extends JpaRepository<Anjiantongji, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM AUTH_USER WHERE name = :name1  OR name = :name2 ")
    List<Anjiantongji> findSQL(@Param("name1") String name1, @Param("name2") String name2);
//    select wj.bmsah as bmsah,wj.gtfzrs as rs from  dwd.d_tyyw_wj_scdb wj where wj.cjsj>'2018-01-01' and wj.cjsj<'2018-08-31';

    @Query(nativeQuery = true, value = "select * from anjiantongji where keshi= :keshi and xiangmu= :xiangmu and yuefen>= :start_month and yuefen<= :end_month ")
    List<Anjiantongji> searchByMonth(@Param("keshi") String keshi, @Param("xiangmu") String xiangmu, @Param("start_month") String start_month, @Param("end_month") String end_month);


//    select t.keshi,t.xiangmu from anjiantongji t where t.yuefen>="2018-02" and t.yuefen<="2018-04";
    @Query(nativeQuery = true, value = "select keshi,xiangmu,sum(anjian_count) as anjian_count,sum(person_count) as person_count from anjiantongji where yuefen>= :start_month and yuefen<= :end_month group by keshi,xiangmu;")
    List<Tuple> getAllTongBi(@Param("start_month") String start_month, @Param("end_month") String end_month);
}
