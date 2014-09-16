/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sina.weibo.sdk.openapi.models;

import org.json.JSONObject;

/**
 * 音乐结构体。.
 * 
 * @author SINA
 * @since 2013-11-22
 */
public class MusicInfo {
    
    //public boolean result; 
    /** The author. */
    public String author;
    
    /** The title. */
    public String title;
    
    /** The album. */
    public String album;
    
    /** The play url. */
    public String playUrl;
    
    /**
	 * Parser.
	 * 
	 * @param jsonString
	 *            the json string
	 * @return the music info
	 */
    public static MusicInfo parser(String jsonString) {
        return null;
    }
    
    /**
	 * Parses the.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the music info
	 */
    public static MusicInfo parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        
        MusicInfo music = new MusicInfo();
        music.author   = jsonObject.optString("author");
        music.title    = jsonObject.optString("title");
        music.album    = jsonObject.optString("album");
        music.playUrl  = jsonObject.optString("playUrl");
        
        return music;
    }
}
