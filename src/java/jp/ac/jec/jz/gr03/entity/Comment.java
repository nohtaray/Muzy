package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Comment {
    public Integer commentId = null;
    public User user = new User();
    public String content = null;
    public Music music = new Music();
    public Integer scorePlusCount = null;
    public Integer scoreMinusCount = null;
    public Date createdAt = null;
    public Boolean isDeleted = null;
}
