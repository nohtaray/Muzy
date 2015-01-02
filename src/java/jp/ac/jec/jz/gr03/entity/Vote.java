package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Vote {
    public Integer voteId = null;
    public User user = new User();
    public Artist artist = new Artist();
    public Integer spentTickets = null;
    public Date createdAt = null;
}
