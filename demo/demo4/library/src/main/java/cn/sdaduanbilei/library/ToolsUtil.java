package cn.sdaduanbilei.library;

/**
 * Created by scorpio on 14-12-12.
 */
public class ToolsUtil {

    /**
     * 判断null和""的情况
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            if (str.length() > 0) {
                return false;
            }
        }
        return true;
    }
}
