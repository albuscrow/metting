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

package com.sina.weibo.sdk.openapi;

import android.util.SparseArray;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * 该类封装了用户接口。
 * 详情请参考<a href="http://t.cn/8F1n1eF">用户接口</a>
 * 
 * @author SINA
 * @since 2014-03-03
 */
public class UsersAPI extends AbsOpenAPI {

    /** The Constant READ_USER. */
    private static final int READ_USER           = 0;
    
    /** The Constant READ_USER_BY_DOMAIN. */
    private static final int READ_USER_BY_DOMAIN = 1;
    
    /** The Constant READ_USER_COUNT. */
    private static final int READ_USER_COUNT     = 2;

    /** The Constant API_BASE_URL. */
    private static final String API_BASE_URL = API_SERVER + "/users";

    /** The Constant sAPIList. */
    private static final SparseArray<String> sAPIList = new SparseArray<String>();
    static {
        sAPIList.put(READ_USER,           API_BASE_URL + "/show.json");
        sAPIList.put(READ_USER_BY_DOMAIN, API_BASE_URL + "/domain_show.json");
        sAPIList.put(READ_USER_COUNT,     API_BASE_URL + "/counts.json");
    }

    /**
	 * Instantiates a new users api.
	 * 
	 * @param accessToken
	 *            the access token
	 */
    public UsersAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }

    /**
	 * 根据用户ID获取用户信息。.
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @param listener
	 *            异步请求回调接口
	 */
    public void show(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
	 * 根据用户昵称获取用户信息。.
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @param listener
	 *            异步请求回调接口
	 */
    public void show(String screen_name, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
	 * 通过个性化域名获取用户资料以及用户最新的一条微博。.
	 * 
	 * @param domain
	 *            需要查询的个性化域名（请注意：是http://weibo.com/xxx后面的xxx部分）
	 * @param listener
	 *            异步请求回调接口
	 */
    public void domainShow(String domain, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        requestAsync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET, listener);
    }
    
    /**
	 * 批量获取用户的粉丝数、关注数、微博数。.
	 * 
	 * @param uids
	 *            需要获取数据的用户UID，多个之间用逗号分隔，最多不超过100个
	 * @param listener
	 *            异步请求回调接口
	 */
    public void counts(long[] uids, RequestListener listener) {
        WeiboParameters params = buildCountsParams(uids);
        requestAsync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET, listener);
    }
    
    /**
	 * -----------------------------------------------------------------------
	 * 请注意：以下方法匀均同步方法。如果开发者有自己的异步请求机制，请使用该函数。
	 * -----------------------------------------------------------------------.
	 * 
	 * @param uid
	 *            the uid
	 * @return the string
	 */
    
    /**
     * @see #show(long, RequestListener)
     */
    public String showSync(long uid) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
	 * Show sync.
	 * 
	 * @param screen_name
	 *            the screen_name
	 * @return the string
	 * @see #show(String, RequestListener)
	 */
    public String showSync(String screen_name) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
	 * Domain show sync.
	 * 
	 * @param domain
	 *            the domain
	 * @return the string
	 * @see #domainShow(String, RequestListener)
	 */
    public String domainShowSync(String domain) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        return requestSync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET);
    }

    /**
	 * Counts sync.
	 * 
	 * @param uids
	 *            the uids
	 * @return the string
	 * @see #counts(long[], RequestListener)
	 */
    public String countsSync(long[] uids) {
        WeiboParameters params = buildCountsParams(uids);
        return requestSync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET);
    }

    /**
	 * Builds the counts params.
	 * 
	 * @param uids
	 *            the uids
	 * @return the weibo parameters
	 */
    private WeiboParameters buildCountsParams(long[] uids) {
        WeiboParameters params = new WeiboParameters();
        StringBuilder strb = new StringBuilder();
        for (long cid : uids) {
            strb.append(cid).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        params.put("uids", strb.toString());
        return params;
    }
}
