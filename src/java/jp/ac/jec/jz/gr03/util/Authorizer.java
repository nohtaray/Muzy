
package jp.ac.jec.jz.gr03.util;

import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.entity.User;

/**
 * ログイン管理
 * 
 * @author yada
 */
public class Authorizer {
    private final HttpSession session;
    private final static String ATTR_NAME = "_loggedInUser";
    
    public Authorizer(HttpSession session) {
        this.session = session;
    }
    
    /**
     * @deprecated より説明的な{@link Authorizer#loginAs(User) }を使用してください。
     * @see Authorizer#loginAs(User) 
     * @param user 
     */
    @Deprecated
    public void login(User user) {
        loginAs(user);
    }
    /**
     * 指定したUserとしてログインします。
     * @param user 
     */
    public void loginAs(User user) {
        session.setAttribute(ATTR_NAME, user);
    }
    /**
     * ログイン中であればログアウトします。
     */
    public void logout() {
        session.removeAttribute(ATTR_NAME);
    }
    /**
     * @deprecated より説明的な{@link Authorizer#getUserLoggedInAs() }を使用してください。
     * @see Authorizer#getUserLoggedInAs() 
     * @return 
     */
    @Deprecated
    public User getUserLoggedIn() {
        return getUserLoggedInAs();
    }
    /**
     * このセッションでログインしている User オブジェクトを取得します。
     * @return ログインしている User。ログインしていなければ null を返します。
     */
    public User getUserLoggedInAs() {
        return (User)session.getAttribute(ATTR_NAME);
    }
    /**
     * 与えられたユーザとしてログインしているかどうかを返します。
     * @param user
     * @return そのユーザとしてログインしていれば true、そうでなければ false
     */
    public boolean hasLoggedInAs(User user) {
        User loggedInUser = getUserLoggedInAs();
        return loggedInUser != null && loggedInUser.userId != null && loggedInUser.userId.equals(user.userId);
    }
    /**
     * ログインしているかどうかを返します。
     * @return ログインしていれば true、そうでなければ false
     */
    public boolean hasLoggedIn() {
        return getUserLoggedInAs() != null;
    }
}
