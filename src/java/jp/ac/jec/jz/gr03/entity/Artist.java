package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Artist {
    public Integer artistId = null;
    public User user = new User();
    public String name = null;
    public String introduction = null;
    public String headerImageFile = null;
    public Date createdAt = null;
    public Date updatedAt = null;
}
