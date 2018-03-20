/**
 * last change 20.03.18 01:28
 */
package com.noteCoin.data.interfaces;

import java.util.List;

public interface WorkWithDB {

    public Integer save(Object object);

    public List load(String requestToDB);

    public Integer remove(Object object);

    public Object find(String searchWord);

    public void reloadConnectWithDB();
}
