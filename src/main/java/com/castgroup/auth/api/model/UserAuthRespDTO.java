package com.castgroup.auth.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthRespDTO {
    
    private String name;
    private String email;
    private String token;
}
