package com.yang.hdyplm.mapper;

import com.yang.hdyplm.pojo.Activity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface ActivityMapper {
    @Select("select * from activity order by id Desc")
    List<Activity> getAll();
    @Update("update activity set status =#{status} where id=#{id}")
    void changeStatus(Integer id,Integer status);
    @Select("select * from activity where id =#{id}")
    Activity getActivityById(Integer id);
    @Delete("delete from activity where id =#{id}")
    void deleteActivity(Integer id);
    @Update("update activity set activity_name = #{activityName},activity_site =#{activitySite},activity_time=#{activityTime}," +
            "activity_type=#{activityType},activity_des=#{activityDes},release_time=#{releaseTime},activity_user=#{activityUser} where id=#{id}")
    void actionUpdate(Activity activity);
    @Insert("insert into activity (activity_name,activity_site,activity_time,activity_type,activity_des,release_time,activity_user,status) values" +
            "(#{activityName},#{activitySite},#{activityTime},#{activityType},#{activityDes},#{releaseTime},#{activityUser},#{status})")
    void actionRelease(Activity activity);
}
