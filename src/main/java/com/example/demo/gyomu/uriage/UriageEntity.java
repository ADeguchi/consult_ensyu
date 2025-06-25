package com.example.demo.gyomu.uriage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UriageEntity {
    private int uriageId;
    private LocalDate uriageYmd;
    private int uriageGaku;
    private List<UriageEntity> uriageList = new ArrayList<>();
    
    // デフォルトコンストラクタ
    public UriageEntity() {}

    // ゲッターとセッター
    public int getUriageId() {
        return uriageId;
    }

    public void setUriageId(int uriageId) {
        this.uriageId = uriageId;
    }

    public LocalDate getUriageYmd() {
        return uriageYmd;
    }

    public void setUriageYmd(LocalDate uriageYmd) {
        this.uriageYmd = uriageYmd;
    }

    public int getUriageGaku() {
        return uriageGaku;
    }

    public void setUriageGaku(int uriageGaku) {
        this.uriageGaku = uriageGaku;
    }
    
    public List<UriageEntity> getUriageList() {
    	return uriageList;
    }
    
    public void setUriageList(List<UriageEntity> uriageList) {
        this.uriageList = uriageList;
    }
}

