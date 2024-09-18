package com.example.demo.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCartRequest {

    private String username;
    private long itemId;
    private int quantity;

}
