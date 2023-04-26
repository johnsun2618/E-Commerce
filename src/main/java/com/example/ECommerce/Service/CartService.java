package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.ResponseDto.CartResponseDto;
import com.example.ECommerce.DTO.ResponseDto.ItemResponseDto;
import com.example.ECommerce.Model.Cart;
import com.example.ECommerce.Model.Customer;
import com.example.ECommerce.Model.Item;
import com.example.ECommerce.Repository.CartRepository;
import com.example.ECommerce.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    public CartResponseDto saveCart(Integer customerId, Item item){

        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        int newTotal = cart.getCartTotal() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setCartTotal(newTotal);
        cart.getItems().add(item);

        cart.setNumberOfItems(cart.getItems().size());
        Cart savedCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .cartTotal(cart.getCartTotal())
                .customerName(customer.getName())
                .numberOfItems(cart.getNumberOfItems())
                .build();

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemEntity: cart.getItems()){

            ItemResponseDto itemResponseDto = new ItemResponseDto();
            itemResponseDto.setPriceOfOneItem(itemEntity.getProduct().getPrice());
            itemResponseDto.setTotalPrice(itemEntity.getRequiredQuantity()*itemEntity.getProduct().getPrice());
            itemResponseDto.setProductName(itemEntity.getProduct().getName());
            itemResponseDto.setQuantity(itemEntity.getRequiredQuantity());

            itemResponseDtoList.add(itemResponseDto);
        }

        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;
    }
}
