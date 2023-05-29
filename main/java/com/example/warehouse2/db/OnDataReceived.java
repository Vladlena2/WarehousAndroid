package com.example.warehouse2.db;

import com.example.warehouse2.adapter.ListItemN;
import com.example.warehouse2.adapter.ListItemU;

import java.util.List;

public interface OnDataReceived {
    void onReceived(List<ListItemN> listItemNS);
}
