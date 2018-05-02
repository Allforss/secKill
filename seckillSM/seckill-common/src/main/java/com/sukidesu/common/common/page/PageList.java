package com.sukidesu.common.common.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixian.yan
 * @date 2018/4/19 下午3:00
 */
@Data
@NoArgsConstructor
public class PageList implements Serializable {

    private static final long serialVersionUID = -2377444201482653486L;

    private long total;
    private List rows;

    public PageList(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }
}
