package com.metacoding.storev2.order;

import com.metacoding.storev2.store.Store;
import com.metacoding.storev2.store.StoreRepository;
import com.metacoding.storev2.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private StoreRepository storeRepository;
    private HttpSession session;

    public OrderService(OrderRepository orderRepository, StoreRepository storeRepository, HttpSession session) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.session = session;
    }

    // 구매 목록 조회
    public List<OrderResponse.ListPage> orderList(int userId) {
        return orderRepository.findAllJoinStore(userId);
    }

    @Transactional
    public void order(int storeId, String fullname, int qty) { // buyer -> fullname
        // 상품 조회
        Store store = storeRepository.findById(storeId);

        // 재고 감소
        store.minusStock(qty);
        storeRepository.updateById(store.getId(), store.getName(), store.getStock(), store.getPrice());

        // log_tb에 구매 목록 저장
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser != null){
            orderRepository.save(storeId, qty, qty * store.getPrice(), fullname, sessionUser.getId());
        } else {
            throw new RuntimeException("로그인이 필요합니다.");
        }
    }
}