package com.metacoding.storev2.store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // 상품 등록
    @Transactional
    public void saveProduct(String name, int stock, int price) {
        storeRepository.save(name, stock, price);
    }

    // 상품 삭제
    @Transactional
    public void deleteProductById(int id) {
        // 상품이 있는지 조회
        Store store = storeRepository.findById(id);

        // 조회한 상품이 null 이라면 예외 발생
        if (store == null) {
            throw new RuntimeException("상품이 없습니다.");
        }
        // 조회한 상품이 있다면 삭제 실행
        storeRepository.deleteById(id);
    }

    // 상품 수정
    @Transactional
    public void updateProductById(int id, String name, int stock, int price) {
        // 상품이 있는지 조회
        Store store = storeRepository.findById(id);

        // 조회한 상품이 null 이라면 예외 발생
        if (store == null) {
            throw new RuntimeException("상품이 없습니다.");
        }

        // 조회한 상품이 있다면 수정 실행
        storeRepository.updateById(id, name, stock, price);
    }

    // 상품 목록 전체 보기
    public List<Store> findAll() { // 상품이 여러개 있으므로 List로 받는 것
        List<Store> storeList = storeRepository.findAll();
        return storeList;
    }

    // 특정 상품 상세 보기
    public Store findById(int id) {
        Store store = storeRepository.findById(id);
        return store;
    }

}
