package com.metacoding.storev2.order;

import com.metacoding.storev2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;
    private HttpSession session;

    public OrderController(OrderService orderService, HttpSession session) {
        this.orderService = orderService;
        this.session = session;
    }

    @PostMapping("/order/save")
    public String save(@RequestParam("storeId") int storeId, @RequestParam("buyer") String buyer, @RequestParam("qty") int qty) {
        orderService.order(storeId, buyer, qty);

        return "redirect:/order";
    }

    // 구매 목록 보기
    @GetMapping("/order")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser != null) {
            List<OrderResponse.ListPage> listPage = orderService.orderList(sessionUser.getId()); // 사용자 ID 전달
            request.setAttribute("models", listPage);
            return "order/list";
        } else {
            return "redirect:/login-form"; // 로그인 페이지로 리다이렉트
        }
    }
}