package com.metacoding.storev2.order;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/save")
    public String save(@RequestParam("storeId") int storeId, @RequestParam("buyer") String buyer, @RequestParam("qty") int qty) {
        orderService.order(storeId, buyer, qty);

        return "redirect:/log";
    }

    @GetMapping("/order")
    public String list(HttpServletRequest request) {
        List<OrderResponse.ListPage> listPage = orderService.orderList();
        request.setAttribute("models", listPage);
        return "order/list";
    }
}
