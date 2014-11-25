package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author yada
 */
public class Music {
    public Integer musicId = null;
    public Artist artist = new Artist();
    public String youtubeVideoId = null;
    public Integer viewCount = null;
    public String title = null;
    public String description = null;
    public Date createdAt = null;
    public Date updatedAt = null;
    public Boolean isDeleted = null;
}
