package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        // 여기서 생성자 사용하면 setter 다 날릴 수 있다.

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

//        Book book = new Book();
//        book.setIsbn(form.getIsbn());
//        book.setId(form.getId());
//        book.setPrice(form.getPrice());
//        book.setName(form.getName());
//        book.setAuthor(form.getAuthor());
//        book.setStockQuantity(form.getStockQuantity());

        // 이 Book 객체는 이미 DB에 한번 저장되어서 나온거라 식별자가 존재한다. -> 즉, 준영속 엔티티!
        // READY -> CANCEL 같은거는 굳이 쿼리문 안날려도 영속 엔티티기 때문에 commit시점에 그냥 DB에 반영된다.
        // 그러나 이런것처럼 준영속 엔티티는 그냥 수정만 하면 DB에 반영 안된다...

        // 그래서 타고 가보면 merge를 쓰는데 이거는 값이 안바뀌면 null이 들어가므로 지금은 merge를 쓰지만
        // 실무에서는 영속 컨텍스트의 값을 변경하는 것(변경 감지 == dirdy checking)으로 해주자. -> merge 쓰지 말자!!

        // 또한, 실무에서는 이렇게 set을 난무하면 안된다.
        // ex) book.change(price, name, stockQuantity)처럼 의미있는 메서드를 만들어서 하자!
        // 변수가 너무 많으면 dto를 만들면된다
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        //itemService.saveItem(book);
        return "redirect:/items";
    }

}
