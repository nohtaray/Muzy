 //Error reading included file Templates/Classes/Templates/Licenses/license-default_1.txt
package jp.ac.jec.jz.gr03.entity;

import java.util.Date;

/**
 *
 * @author 12jz0121
 */


public class MylistDetail {
    public Integer mylistDetailId = null;
    public Mylist mylist = new Mylist();
    public Integer musicId = null;
    public Integer artistId = null;
    public Date createdAt = null;
    public Date updatedAt = null;
}
