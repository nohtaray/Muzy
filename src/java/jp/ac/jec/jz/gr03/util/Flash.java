package jp.ac.jec.jz.gr03.util;

import java.util.LinkedList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yada
 */
public class Flash {

    private final static String ATTR_NAME = "_flash";
    public final FlashQueue success;
    public final FlashQueue info;
    public final FlashQueue warning;
    public final FlashQueue danger;

    public static Flash get(HttpSession session) {
        Flash flash = (Flash) session.getAttribute(ATTR_NAME);
        if (flash == null) {
            flash = new Flash();
            session.setAttribute(ATTR_NAME, flash);
        }
        return flash;
    }

    private Flash() {
        this.success = new FlashQueue();
        this.info = new FlashQueue();
        this.warning = new FlashQueue();
        this.danger = new FlashQueue();
    }

    // TODO: implement java.util.Queue
    public class FlashQueue {

        private final LinkedList<String> queue;

        private FlashQueue() {
            this.queue = new LinkedList<>();
        }

        public boolean offer(String message) {
            return this.queue.offer(message);
        }

        public String poll() {
            return this.queue.poll();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        public int size() {
            return this.queue.size();
        }

        public void clear() {
            this.queue.clear();
        }
    }
}
