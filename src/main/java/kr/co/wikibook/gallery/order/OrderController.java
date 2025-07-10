package kr.co.wikibook.gallery.order;

import kr.co.wikibook.gallery.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final ItemService itemService;
}
