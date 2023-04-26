package com.example.ECommerce.Service;

import com.example.ECommerce.Enum.ProductStatus;
import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.Exception.InvalidCustomerException;
import com.example.ECommerce.Exception.InvalidProductException;
import com.example.ECommerce.Model.Customer;
import com.example.ECommerce.Model.Item;
import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Repository.CustomerRepository;
import com.example.ECommerce.Repository.ItemRepository;
import com.example.ECommerce.Repository.ProductRepository;
import com.example.ECommerce.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    public Item addItem(ItemRequestDto itemRequestDto) throws Exception {

        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Product product;
        try{
            product = productRepository.findById(itemRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new InvalidProductException("Product doesn't exist");
        }

        if(itemRequestDto.getRequiredQuantity()>product.getQuantity() || product.getProductStatus()!= ProductStatus.AVAILABLE){
            throw new Exception("Product out of Stock");
        }

        Item item = ItemTransformer.ItemRequestDtoToItem(itemRequestDto);
        item.setCart(customer.getCart());
        item.setProduct(product);

        product.getItemList().add(item);
        Product savedProduct = productRepository.save(product);

        // Think - only saving child will also work here ?????????

        int size = product.getItemList().size();
        return savedProduct.getItemList().get(size-1);
    }
}
