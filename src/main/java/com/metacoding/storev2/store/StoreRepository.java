package com.metacoding.storev2.store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 데이터베이스와 연결된 클래스 라고 알려주는 역할 
public class StoreRepository {

    private EntityManager em; // 데이터베이스와 소통하는 도구 역할

    public StoreRepository(EntityManager em) {
        this.em = em;
    }

    // 상품 등록
    public void save(String name, int stock, int price) {
        Query query = em.createNativeQuery("insert into store_tb (name, stock, price) values (?, ?, ?)");
        query.setParameter(1, name);
        query.setParameter(2, stock);
        query.setParameter(3, price);
        query.executeUpdate();
    }

    // 상품 삭제
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from store_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    // 상품 수정
    public void updateById(int id, String name, int stock, int price) {
        Query query = em.createNativeQuery("update store_tb set name = ?, stock = ?, price = ? where id = ?");
        query.setParameter(1, name);
        query.setParameter(2, stock);
        query.setParameter(3, price);
        query.setParameter(4, id);
        query.executeUpdate();
    }

    // 상품 목록 전체 보기
    public List<Store> findAll() {
        Query query = em.createNativeQuery("select * from store_tb order by id desc", Store.class);
        return query.getResultList();
    }

    // 특정 상품 상세 보기
    public Store findById(int id) {
        Query query = em.createNativeQuery("select * from store_tb where id = ?", Store.class);
        query.setParameter(1, id);
        return (Store) query.getSingleResult();
    }
}
