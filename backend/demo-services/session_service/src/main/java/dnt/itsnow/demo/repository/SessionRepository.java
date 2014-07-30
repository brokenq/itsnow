/**
 * Developer: Kadvin Date: 14-7-4 上午9:26
 */
package dnt.itsnow.demo.repository;

import dnt.itsnow.demo.model.Session;
import org.apache.ibatis.annotations.*;

/**
 * <h1>映射用户会话</h1>
 *
 * 默认的做法（无需自己指定SQL）:
 * <pre>
 *     selectSession(int):Session;
 *     selectSessions():List&lt;Session&gt;
 *       or
 *     <code>@MapKey("session_id")</code>
 *     selectSessions():Map&lt;String,Session&gt;
 *     insertSession(Session):int
 *     updateSession(Session):int
 *     deleteSession(int):int
 * </pre>
 */
public interface SessionRepository {
    @Select("select * from demo_sessions where session_id = #{sessionId}")
    Session findById(@Param("sessionId") String sessionId);

    @Delete("delete from demo_sessions where session_id = #{sessionId")
    void destroy(@Param("sessionId") String sessionId);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into demo_sessions(session_id, data, created_at, updated_at) " +
            "values(#{sessionId}, #{data}, #{createdAt}, #{updatedAt})")
    void save(Session session);
}
