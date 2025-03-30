package com.metacoding.storev2.order;

import lombok.Data;

public class OrderResponse {

    @Data
    public static class ListPage {
        private int id;
        private String name;
        private int qty;
        private int totalPrice;
        private String fullname;
        private int userOrderId; // 사용자 ID별 순차적 아이디 추가

        public ListPage(int id, String name, int qty, int totalPrice, String fullname) {
            this.id = id;
            this.name = name;
            this.qty = qty;
            this.totalPrice = totalPrice;
            this.fullname = fullname;
        }

        public ListPage(int id, String name, int qty, int totalPrice, String fullname, int userOrderId) {
            this.id = id;
            this.name = name;
            this.qty = qty;
            this.totalPrice = totalPrice;
            this.fullname = fullname;
            this.userOrderId = userOrderId;
        }

        @Override
        public String toString() {
            return "ListPage [id=" + id + ", name=" + name + ", qty=" + qty + ", totalPrice=" + totalPrice + ", fullname=" + fullname + ", userOrderId=" + userOrderId + "]";
        }
    }
}