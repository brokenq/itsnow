package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by User on 2014/11/10.
 */
public interface GeneralUserRespository extends CommonUserRepository{
    long count(@Param("keyword")String keyword,@Param("account")Account account);

    List<User> findAll(@Param("keyword")String keyword,@Param("pageable")Pageable pageable,@Param("account")Account account);
}
