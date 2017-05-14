package com.youthlin.blog.model.po;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:00.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class BaseMeta {
    Long metaId;
    String metaKey;
    String metaValue;

    @Override
    public String toString() {
        return "BaseMeta{" +
                "metaId=" + metaId +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                '}';
    }

    //region getter setter

    public Long getMetaId() {
        return metaId;
    }

    public BaseMeta setMetaId(Long metaId) {
        this.metaId = metaId;
        return this;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public BaseMeta setMetaKey(String metaKey) {
        this.metaKey = metaKey;
        return this;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public BaseMeta setMetaValue(String metaValue) {
        this.metaValue = metaValue;
        return this;
    }
    //endregion

}
