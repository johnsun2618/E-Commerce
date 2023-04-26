package com.example.ECommerce.Transformer;

import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.Model.Item;

public class ItemTransformer {

    public static Item ItemRequestDtoToItem(ItemRequestDto itemRequestDto){
        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();
    }
}
