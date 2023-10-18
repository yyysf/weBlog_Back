package com.yang.hdyplm.service.impl;

import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.mapper.ActivityMapper;
import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.Activity;
import com.yang.hdyplm.service.ActivityService;
import com.yang.hdyplm.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
       @Autowired
       private ActivityMapper activityMapper;
       @Autowired
       private UserMapper userMapper;

       @Override
       public List<Activity> getActivities() {
              List<Activity> activities = activityMapper.getAll();
              return activities;
       }

       @Override
       public void changeStatus(Integer id) {
              Activity activity = activityMapper.getActivityById(id);
              int status = activity.getStatus();
              if (status==0){
                     activityMapper.changeStatus(id,1);
              }else {
                     activityMapper.changeStatus(id,0);
              }
       }

       @Override
       public void deleteActivity(Integer id) {
              activityMapper.deleteActivity(id);
       }

       @Override
       public Activity getActivityById(Integer id) {
              Activity activity = activityMapper.getActivityById(id);
              return activity;
       }

       @Override
       public void actionUpdate(Integer id, Activity activity) {
              activity.setActivityTime(DateUtil.Dateime(activity.getActivityDate(),activity.getActivityTime1()));


              Date date = new Date();
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


              activity.setReleaseTime(formatter.format(date));
              activity.setActivityUser(userMapper.getById(Math.toIntExact(BaseContext.getCurrentId())).getName());
              activityMapper.actionUpdate(activity);
       }

       @Override
       public void actionRelease(Activity activity) {
              activity.setStatus(0);
              activity.setActivityTime(DateUtil.Dateime(activity.getActivityDate(),activity.getActivityTime1()));

              Date date = new Date();
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
              activity.setReleaseTime(formatter.format(date));

              activity.setActivityUser(userMapper.getById(Math.toIntExact(BaseContext.getCurrentId())).getName());
              activityMapper.actionRelease(activity);
       }
}
