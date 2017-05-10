package com.youthlin.blog.model.bo;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 10:19.
 */
public class Page<T> implements Pageable<T> {
    private long totalPage;
    private long currentPage;
    private long pageSize;
    private long totalRow;
    private long startRow;
    private long endRow;
    private long size;
    private List<T> list = Lists.newArrayList();

    public Page() {
    }

    public Page(PageInfo<T> pageInfo) {
        list.addAll(pageInfo.getList());
        totalPage = pageInfo.getPages();
        currentPage = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
        totalRow = pageInfo.getTotal();
        startRow = pageInfo.getStartRow();
        endRow = pageInfo.getEndRow();
        size = pageInfo.getSize();
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalRow=" + totalRow +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", size=" + size +
                ", list=" + list +
                '}';
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public long getTotalPage() {
        return totalPage;
    }

    @Override
    public long getCurrentPage() {
        return currentPage;
    }

    @Override
    public long getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalRow() {
        return totalRow;
    }

    @Override
    public long getStartRow() {
        return startRow;
    }

    @Override
    public long getEndRow() {
        return endRow;
    }

    @Override
    public long getSize() {
        return size;
    }
}
