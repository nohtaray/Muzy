package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Message {
    public Artist artist = new Artist();
    public Integer messageId = null;
    public User user = new User();
    public String content = null;
    public Message responseTo = null;
    public Date createdAt = null;
    public Boolean isDeleted = null;
}
