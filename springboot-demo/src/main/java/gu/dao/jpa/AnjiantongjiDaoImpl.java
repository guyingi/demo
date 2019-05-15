package gu.dao.jpa;//package report.dao;

import gu.entity.Anjiantongji;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
public class AnjiantongjiDaoImpl {

    @PersistenceContext
    EntityManager entityManager;

    List<Tuple> searchByMonth(String keshi, String xiangmu, String start_month, String end_month) {
//        EntityManager entityManager =
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Anjiantongji> root = query.from(Anjiantongji.class);
        Path<String> keshi1Path = root.get("keshi");
        Path<String> xiangmuPath = root.get("xiangmu");
        Path<String> yuefenPath = root.get("yuefen");
        Path<Long> anjianCountPath = root.get("anjian_count");
        Path<Long> personCountPath = root.get("person_count");

        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtils.isEmpty(keshi)) {
            predicateList.add(cb.equal(keshi1Path, keshi));
        }
        if (!StringUtils.isEmpty(xiangmu)) {
            predicateList.add(cb.equal(xiangmuPath, xiangmu));
        }
        if (!StringUtils.isEmpty(start_month) && !StringUtils.isEmpty(end_month)) {
            predicateList.add(
                    cb.between(yuefenPath, start_month, end_month)
            );
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);
        query.where(predicates);//where条件加上
        query.select(cb.tuple(keshi1Path,xiangmuPath, cb.count(anjianCountPath),cb.count(personCountPath)));

        //query.multiselect(statusPath, cb.count(root));//
        TypedQuery<Tuple> q = entityManager.createQuery(query);
        List<Tuple> result = q.getResultList();
        System.out.println(result.size());
        return null;
    }

}
