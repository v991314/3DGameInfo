package com.gavin.your3dmgame.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.gavin.your3dmgame.entity.GameListItem;
import com.gavin.your3dmgame.entity.chapter_item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaVin on 2016/1/19
 * json解析工具类
 */
public class JsonUtils {
    /*
     * 解析文章json数据的方法
     *
     * @param json 网络下载的json数据
     * @return
     */
    public static List<chapter_item> parseChapterJson(String json) {
        List<chapter_item> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(removeBOM(json));
            JSONObject data = object.optJSONObject("data");
            for (int index = 0; index < data.length(); index++) {
                //根据序号，用fastJson进行json解析
                String fastJson = data.optString(index + "");
                chapter_item chapter_item = JSON.parseObject(fastJson, chapter_item.class);
                list.add(chapter_item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 游戏列表json解析
     *
     * @param json
     * @return
     */
    public static List<GameListItem> parseGameJson(String json) {
        List<GameListItem> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(removeBOM(json));
            JSONObject data = (JSONObject) object.get("data");
            for (int index = 0; index < data.length(); index++) {
                //根据序号，用fastJson进行json解析
                String fastJson = data.optString(index + "");
                GameListItem gameListItem = JSON.parseObject(fastJson, GameListItem.class);
                list.add(gameListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 异常信息：org.json.JSONException: Value ﻿ of type java.lang.String cannot be converted to JSONObject
     * json串头部出现字符："\ufeff" 解决方法
     * @param data
     * @return
     */
    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }

}
