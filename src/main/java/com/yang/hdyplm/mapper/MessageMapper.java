package com.yang.hdyplm.mapper;

import com.yang.hdyplm.dto.MessageDTO;
import com.yang.hdyplm.pojo.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface MessageMapper {
    @Insert("insert into message (message_id,message_content,message_time,message_uid) values (#{messageId},#{messageContent},#{messageTime},#{messageUid})")
    void save(Message message);
    @Select("select * from user,message where user.id=message.message_uid order by message_id desc")
    List<MessageDTO> getMsg();
    @Delete("delete from message where message_id =#{messageId}")
    void del(Integer messageId);
    @Update("update message set message_content =#{messageContent} where message_id =#{messageId}")
    void update(Integer messageId, String messageContent);
}
