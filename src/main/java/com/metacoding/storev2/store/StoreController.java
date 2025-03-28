package com.metacoding.storev2.store;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StoreController {

    private StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // 상품 등록
    // 홈 화면에서 상품 등록을 눌렀을 때 화면 넘겨주기
    @GetMapping("/store/save-form")
    public String saveForm() {
        return "store/save-form";
    }

    // 상품 등록 페이지에서 상품 등록 버튼을 눌렀을 때 실행하는 메소드
    @PostMapping("/store/save")
    public String save(@RequestParam("name") String name,
                       @RequestParam("stock") int stock,
                       @RequestParam("price") int price) {

        storeService.saveProduct(name, stock, price);

        // 등록이 완료되면 홈 화면으로 리다이렉트
        return "redirect:/";
    }

    // 상품 삭제
    @PostMapping("/store/{id}/delete") // mustache에서 삭제 버튼을 눌렀을 때 실행하는 메소드
    public String delete(@PathVariable("id") int id) {

        storeService.deleteProductById(id);

        // 삭제가 완료되면 홈 화면으로 리다이렉트
        return "redirect:/";
    }

    // 상품 수정
    @GetMapping("/store/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        request.setAttribute("model", storeService.findById(id));
        return "store/update-form";
    }

    @PostMapping("/store/{id}/update")
    public String update(@PathVariable("id") int id,
                         @RequestParam("name") String name,
                         @RequestParam("stock") int stock,
                         @RequestParam("price") int price) {
        storeService.updateProductById(id, name, stock, price);

        // 수정이 완료되면 store/id 화면으로 이동
        return "redirect:/store/" + id;
    }

    // 상품 목록 전체 보기
    @GetMapping("/")
    public String list(HttpServletRequest request) {
        List<Store> store = storeService.findAll();
        request.setAttribute("models", store);
        return "store/list";
    }

    // 특정 상품 상세 보기
    @GetMapping("/store/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        Store store = storeService.findById(id);
        request.setAttribute("model", store);
        return "store/detail";
    }
}
