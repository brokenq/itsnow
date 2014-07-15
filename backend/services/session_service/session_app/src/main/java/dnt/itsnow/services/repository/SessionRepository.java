/**
 * Developer: Kadvin Date: 14-7-4 上午9:26
 */
package dnt.itsnow.services.repository;

import dnt.itsnow.services.model.Session;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 映射用户会话
 */
public interface SessionRepository {
    @Select("select * from sessions where session_id = {sessionId}")
    Session findById(String sessionId);

    @Delete("delete from sessions where session_id = {sessionId")
    void destroy(String sessionId);

    @Insert("insert into sessions(id, data, created_at, updated_at) " +
            "values({session.sessionId, session.toBinary(), now(), now())")
    void save(Session session);
}
