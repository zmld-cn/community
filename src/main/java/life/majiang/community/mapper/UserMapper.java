package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO t_user (name,account_id,token,gmt_create,gmt_motified ) " +
            "VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtMotified})")
    public void insert(User user) ;

    @Select("SELECT name, token from t_user where token = #{token}")
    User findByToken(@Param("token") String token);
}
