package com.yang.hdyplm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotosDto {
    private String name;
    private String time;
    private String desc;
    private String coverUrl;
    private String[] photosUrl;
}
