package com.metacoding.storev2.order;

import lombok.Data;

public class OrderResponse {

    // 화면에 필요한 데이터만 있는 오브젝트
    @Data
    public static class ListPage {
        private int id;
        private String name;
        private int qty;
        private int totalPrice;
        private String buyer;

        public ListPage(int id, String name, int qty, int totalPrice, String buyer) {
            this.id = id;
            this.name = name;
            this.qty = qty;
            this.totalPrice = totalPrice;
            this.buyer = buyer;
        }

        @Override
        public String toString() {
            return "ListPage [id=" + id + ", name=" + name + ", qty=" + qty + ", totalPrice=" + totalPrice + ", buyer=" + buyer + "]";
        }
    }
}
