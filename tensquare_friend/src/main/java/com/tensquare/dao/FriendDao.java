package com.tensquare.dao;

import com.tensquare.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    /**
     * 根据用户ID与被关注用户ID查询记录个数
     * @param userid
     * @param friendid
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM tb_friend WHERE userid=? AND friendid=?", nativeQuery = true)
    public  int selectCount(String userid,String friendid);

    @Modifying
    @Query(value = "update tb_friend set islike=? where userid=? and friendid=?",nativeQuery = true)
    public  void updateLike(String islike,String userid,String friendid);
}

