package com.zhangli.json.map;

import java.util.List;

/**
 * Created by scxh on 2016/1/4.
 */
public class Info {
    private List<MerchantKey> merchantKey;
    private PageInfo pageInfo;

    public List<MerchantKey> getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(List<MerchantKey> merchantKey) {
        this.merchantKey = merchantKey;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
