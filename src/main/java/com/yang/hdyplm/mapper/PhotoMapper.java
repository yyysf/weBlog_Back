package com.yang.hdyplm.mapper;

import com.yang.hdyplm.dto.PhotosDto;
import com.yang.hdyplm.pojo.Photo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PhotoMapper {
    @Select("select * from photo")
    List<Photo> photoList();
    /*@Insert("INSERT INTO `photo` (`name`, `time`, `desc`, `cover_url`,`views`,`release_user`,`p1`, `p2`, `p3`, `p4`, `p5`, `p6`, `p7`, `p8`, `p9`, `p10`, `p11`, `p12`) VALUES " +
            "(#{name},#{time},#{desc},#{coverUrl},#{views},#{releaseUser},#{p1},#{p2},#{p3},#{p4},#{p5},#{p6},#{p7},#{p8},#{p9},#{p10},#{p11},#{p12})")*/
    int insertForm(Photo photo);
    @Select("select * from photo where id=#{id}")
    Photo photoListById(Integer id);
    @Delete("delete from photo where id=#{id}")
    Integer delPhotos(Integer id);
    @Update("update photo set `name`=#{name}, `time`=#{time}, `desc`=#{desc}, `cover_url`=#{coverUrl},`p1`=#{p1}, `p2`=#{p2}, `p3`=#{p3}, `p4`=#{p4}, `p5`=#{p5}, `p6`=#{p6}, `p7`=#{p7}, " +
            "`p8`=#{p8}, `p9`=#{p9}, `p10`=#{p10}, `p11`=#{p11}, `p12`=#{p12} where id=#{id}")
    void updatePhotos(Photo photo);
    @Update("update photo set views=#{views} where id=#{id}")
    void updateViews(String id,Integer views);
}
