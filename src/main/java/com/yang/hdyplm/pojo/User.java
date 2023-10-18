package com.yang.hdyplm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String name;
    private String img;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;
    private String introduction;
    private String email;
}
