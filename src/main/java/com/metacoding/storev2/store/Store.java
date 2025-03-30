package com.metacoding.storev2.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "store_tb")
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer stock;
    private Integer price;

    public void minusStock(int qty) {

        // 마이너스 재고 방지
        if (this.stock < qty) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        // 재고 감소
        this.stock = this.stock - qty;
    }

}
