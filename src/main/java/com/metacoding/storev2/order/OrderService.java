package com.metacoding.storev2.order;

import com.metacoding.storev2.store.Store;
import com.metacoding.storev2.store.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private StoreRepository storeRepository;

    public OrderService(OrderRepository orderRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
    }

    // 주문 목록 조회
    public List<OrderResponse.ListPage> orderList() {
        return orderRepository.findAllJoinStore();
    }

    public void order(int storeId, String buyer, int qty) {
        // 상품 조회
        Store store = storeRepository.findById(storeId);

        // 재고 감소
        store.minusStock(qty);
        storeRepository.updateById(store.getId(), store.getName(), store.getStock(), store.getPrice());

        // log_tb에 구매 목록 저장
        orderRepository.save(storeId, qty, qty * store.getPrice(), buyer);
    }
}
