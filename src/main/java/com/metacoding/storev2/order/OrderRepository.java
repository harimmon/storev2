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

    // 주문 목록 불러오기
    public List<OrderResponse.ListPage> findAllJoinStore() {

        // 주문 데이터를 담을 리스트
        List<OrderResponse.ListPage> orderList = new ArrayList<>();

        String sql = "SELECT lt.id, st.name, lt.qty, lt.total_price, lt.buyer FROM log_tb lt INNER JOIN store_tb st ON lt.store_id = st.id ORDER BY lt.id DESC";
        Query query = em.createNativeQuery(sql);

        // DB에서 조회한 결과를 리스트로 반환
        List<Object[]> obsList = (List<Object[]>) query.getResultList();
        for (Object[] obs : obsList) {
            OrderResponse.ListPage log = new OrderResponse.ListPage(
                    (int) obs[0],
                    (String) obs[1],
                    (int) obs[2],
                    (int) obs[3],
                    (String) obs[4]);
            orderList.add(log);
        }
        return orderList;
    }

    // 주문 정보를 log_tb에 저장
    public void save(int storeId, int qty, int totalPrice, String buyer) {
        Query query = em.createNativeQuery("insert into log_tb(store_id, qty, total_price, buyer) values(?, ?, ?, ?)");
        query.setParameter(1, storeId);
        query.setParameter(2, qty);
        query.setParameter(3, totalPrice);
        query.setParameter(4, buyer);
        query.executeUpdate();
    }
}
