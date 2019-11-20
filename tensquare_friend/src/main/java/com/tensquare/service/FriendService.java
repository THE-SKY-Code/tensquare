package com.tensquare.service;

import com.tensquare.dao.FriendDao;
import com.tensquare.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    public  int addFriend(String userid,String friemdid){
        //判断是否已经添加过好友
        if(friendDao.selectCount(userid,friemdid)> 0){
            return  0;
        }
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friemdid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断对方是否喜欢你，如果喜欢讲islike设置为1
        if(friendDao.selectCount(friemdid,userid)>0){
            friendDao.updateLike("1",userid,friemdid);
            friendDao.updateLike("1",friemdid,userid);
        }

        return  1;
    }
}
