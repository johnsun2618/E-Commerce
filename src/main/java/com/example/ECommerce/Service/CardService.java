package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.RequestDto.CardRequestDto;
import com.example.ECommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.ECommerce.DTO.ResponseDto.CardResponseDto;
import com.example.ECommerce.Exception.InvalidCustomerException;
import com.example.ECommerce.Exception.MobileNoAlreadyPresentException;
import com.example.ECommerce.Model.Card;
import com.example.ECommerce.Model.Customer;
import com.example.ECommerce.Repository.CustomerRepository;
import com.example.ECommerce.Transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CustomerRepository customerRepository;

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException {

        Customer customer = customerRepository.findByMobNo(cardRequestDto.getMobNo());
        if(customer==null){
            throw new InvalidCustomerException("Sorry! The customer doesn't exists");
        }

        Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);

        customer.getCards().add(card);
        customerRepository.save(customer);

        // response dto
        return CardResponseDto.builder()
                .customerName(customer.getName())
                .cardNo(card.getCardNo())
                .build();

    }
}
