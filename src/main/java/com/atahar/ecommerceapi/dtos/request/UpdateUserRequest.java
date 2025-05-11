package com.atahar.ecommerceapi.dtos.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
