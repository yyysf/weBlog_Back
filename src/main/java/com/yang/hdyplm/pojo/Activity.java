package com.yang.hdyplm.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {
    private Integer id;
    private String activityName;
    private String activitySite;
    private String activityTime;
    private String activityType;
    private String activityDes;
    private String releaseTime;
    private int status;
    private String activityDate;
    private String activityTime1;
    private String activityUser;
}
