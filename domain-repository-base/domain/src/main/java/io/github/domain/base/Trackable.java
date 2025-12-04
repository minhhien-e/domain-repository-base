package io.github.domain.base;

import java.util.Set;

public interface Trackable {

    /**
     * Lấy tất cả property đang bị thay đổi.
     */
    Set<String> getChangedProperties();

    /**
     * Đánh dấu một property là đã thay đổi.
     */
    void markChanged(String property);

    /**
     * Xóa trạng thái đã thay đổi sau khi persist.
     */
    void clearChanges();

    /**
     * Kiểm tra object có thay đổi không.
     */
    boolean hasChanges();
}
