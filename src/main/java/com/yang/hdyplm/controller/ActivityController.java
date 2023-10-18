package com.yang.hdyplm.controller;

import com.yang.hdyplm.pojo.Activity;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/hdyplm/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/activityList")
    public Result getActivities(){
        List<Activity> activities =activityService.getActivities();
        Map data=new HashMap();
        data.put("rows",activities);
        return Result.success(data);

    }
    @PutMapping("/changeStatus/{id}")
    public Result changeStatus(@PathVariable("id")Integer id){
        activityService.changeStatus(id);
        return Result.success();
    }
    @PutMapping("/deleteActivity/{id}")
    public Result deleteActivity(@PathVariable("id")Integer id){
        activityService.deleteActivity(id);
        return Result.success();
    }
    @GetMapping("/getActivityById/{id}")
    public Result getActivityById(@PathVariable("id")Integer id){
        Activity activity = activityService.getActivityById(id);
        Map data=new HashMap();
        data.put("rows",activity);
        return Result.success(data);
    }
    @PutMapping("/actionUpdate/{id}")
    public Result actionUpdate(@PathVariable("id")Integer id,@RequestBody Activity activity){
            activityService.actionUpdate(id,activity);
            return Result.success();
    }
    @PostMapping("/actionRelease")
    public Result actionRelease(@RequestBody Activity activity){
        activityService.actionRelease(activity);
        return Result.success();
    }
}

