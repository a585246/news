package com.cskaoyan.news.utils;

import com.cskaoyan.news.bean.NewVo;

import java.util.Comparator;
import java.util.Date;

public class NewVoComparator  implements Comparator<NewVo> {
    @Override
    public int compare(NewVo o1, NewVo o2) {
      double score1=  getScore(o1);
        double score2=getScore(o2);
            if(score1>score2)
                return 1;
            else {
                if (score2 < score2)
                    return -1;
                else return 0;
            }
    }

    private double getScore(NewVo o1) {
        Integer likeCount = o1.getNews().getLikeCount();
        if(likeCount==0)
        {
            likeCount=1;
        }
        long currentDate = new Date().getTime();
        long createdDate = o1.getNews().getCreatedDate().getTime();
        long l = currentDate - createdDate;
        double log = Math.log(likeCount);
        double v = log + l / 45000;
        return v;
    }
}
