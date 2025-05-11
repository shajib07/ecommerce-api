package com.atahar.ecommerceapi.mappers;

import com.atahar.ecommerceapi.dtos.CartDto;
import com.atahar.ecommerceapi.dtos.CartItemDto;
import com.atahar.ecommerceapi.entities.Cart;
import com.atahar.ecommerceapi.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}