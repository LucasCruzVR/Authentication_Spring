package com.castgroup.auth.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;
    private String password;
}
