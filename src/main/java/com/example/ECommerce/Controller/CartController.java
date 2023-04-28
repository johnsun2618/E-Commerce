package com.example.ECommerce.Controller;

import com.example.ECommerce.DTO.RequestDto.CheckoutCartRequestDto;
import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.DTO.ResponseDto.CartResponseDto;
import com.example.ECommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.ECommerce.DTO.ResponseDto.OrderResponseDto;
import com.example.ECommerce.Model.Item;
import com.example.ECommerce.Service.CartService;
import com.example.ECommerce.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception {

        try{
            Item savedItem = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.saveCart(itemRequestDto.getCustomerId(),savedItem);
            return new ResponseEntity(cartResponseDto,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public OrderResponseDto checkOutCart(@RequestBody CheckoutCartRequestDto checkoutCartRequestDto) throws Exception {

        return cartService.checkOutCart(checkoutCartRequestDto);
    }

//    @GetMapping("/items")
//    public ResponseEntity<List<ItemResponseDto>> viewCartItems(@RequestParam("customerId") Long customerId) {
//        try {
//            List<ItemResponseDto> cartItems = cartService.Item(customerId);
//            return new ResponseEntity<>(cartItems, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    // remove from cart

    // view all items in cart

    // email sending

    // my email - kunaljindal995@gmail.com


}

