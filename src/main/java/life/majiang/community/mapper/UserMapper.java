package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO t_user (name,account_id,token,gmt_create,gmt_motified ) " +
            "VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtMotified})")
    public void insert(User user) ;
}
