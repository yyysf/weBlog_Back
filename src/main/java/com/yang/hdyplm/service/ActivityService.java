package com.yang.hdyplm.service;

import com.yang.hdyplm.pojo.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> getActivities();

    void changeStatus(Integer id);

    void deleteActivity(Integer id);

    Activity getActivityById(Integer id);

    void actionUpdate(Integer id,Activity activity);

    void actionRelease(Activity activity);
}
