package io.github.domain.base;

import java.util.Map;
import java.util.Set;

public interface NestedTrackable {
    /**
     * Đánh dấu field của entity con đã thay đổi
     */
    void markNestedChanged(Trackable child, String property);

    /**
     * Lấy tất cả entity con và các field dirty của nó
     */
    Map<Trackable, Set<String>> getNestedChangedProperties();

    /**
     * Xóa trạng thái dirty
     */
    void clearChanges();

    /**
     * Kiểm tra có entity con nào dirty không
     */
    boolean hasChanges();
}
