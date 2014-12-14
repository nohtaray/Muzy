package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Advertisement {
    public Integer advertisementId = null;
    public User user = new User();
    public Music music = new Music();
    public Integer spentPoints = null;
    public Date createdAt = null;
    public Date updatedAt = null;
}
