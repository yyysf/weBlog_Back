package com.yang.hdyplm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    private Integer messageId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date messageTime;
    private String messageContent;
    private String img;
    private String messageUid;
}
