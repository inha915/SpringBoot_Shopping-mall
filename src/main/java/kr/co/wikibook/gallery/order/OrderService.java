package kr.co.wikibook.gallery.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.cart.CartMapper;
import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.OrderGetRes;
import kr.co.wikibook.gallery.order.model.OrderItemPostDto;
import kr.co.wikibook.gallery.order.model.OrderPostDto;
import kr.co.wikibook.gallery.order.model.OrderPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ItemMapper itemMapper;
    private final CartMapper cartMapper;


    @Transactional
    public int saveOrder(OrderPostReq req, int logginedMemberId) {
      //상품정보 DB로부터 가져온다
        List<ItemGetRes> itemList = itemMapper.findAllByIdIn(req.getItemIds());
        log.info("itemList={}", itemList);
        int amount = 0;
        for(ItemGetRes item : itemList){
            amount += item.getPrice()-(item.getPrice() * item.getDiscountPer()/100);
        }
        log.info("amount={}",amount);
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMemberId(logginedMemberId);
        orderPostDto.setName(req.getName());
        orderPostDto.setAddress(req.getAddress());
        orderPostDto.setPayment(req.getPayment());
        orderPostDto.setCardNumber(req.getCardNumber());
        orderPostDto.setAmount(amount);
        log.info("before-orderPostDto={}", orderPostDto);


        int result = orderMapper.save(orderPostDto);
        log.info("after-orderPostDto={}", orderPostDto);
        OrderItemPostDto orderItemPostDto = new OrderItemPostDto(orderPostDto.getOrderId(),req.getItemIds());

        int result2 = orderItemMapper.save(orderItemPostDto);
        cartMapper.deleteCartByMemberId(logginedMemberId);
        return 1;
    }
    public List<OrderGetRes> findAllByMemberIdOrderByIdDesc(int memberId) {
        return orderMapper.findAllByMemberIdOrderByIdDesc(memberId);
    }
}
