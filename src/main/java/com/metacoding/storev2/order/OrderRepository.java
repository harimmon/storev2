package com.metacoding.storev2.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    // 주문 목록 불러오기 (수정된 부분)
    public List<OrderResponse.ListPage> findAllJoinStore(int userId) {

        // 주문 데이터를 담을 리스트
        List<OrderResponse.ListPage> orderList = new ArrayList<>();

        String sql = "SELECT ot.id, st.name, ot.qty, ot.total_price, ut.fullname " +
                "FROM order_tb ot INNER JOIN store_tb st ON ot.store_id = st.id " +
                "INNER JOIN user_tb ut ON ot.user_id = ut.id WHERE ot.user_id = ? ORDER BY ot.id DESC"; // user_id 조건 추가
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, userId); // user_id 파라미터 설정

        // DB에서 조회한 결과를 리스트로 반환
        List<Object[]> obsList = (List<Object[]>) query.getResultList();
        for (Object[] obs : obsList) {
            OrderResponse.ListPage log = new OrderResponse.ListPage(
                    (int) obs[0],
                    (String) obs[1],
                    (int) obs[2],
                    (int) obs[3],
                    (String) obs[4]); // fullname 추가
            orderList.add(log);
        }
        return orderList;
    }

    // 주문 정보를 log_tb에 저장
    public void save(int storeId, int qty, int totalPrice, String fullname, int userId) { // buyer -> fullname, userId 추가
        Query query = em.createNativeQuery("insert into order_tb(store_id, qty, total_price, user_id) values(?, ?, ?, ?)"); // fullname -> user_id
        query.setParameter(1, storeId);
        query.setParameter(2, qty);
        query.setParameter(3, totalPrice);
        query.setParameter(4, userId); // fullname -> userId
        query.executeUpdate();
    }
}