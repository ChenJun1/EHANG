package com.cvnavi.logistics.i51ehang.app.callback.manager;

import okhttp3.Call;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/2/5 上午9:38
 * 描述：所有带网络请求的 的监听
 ************************************************************************************/

public interface HttpDelWithListener {

    public void httpError(Call call, Exception e, int id);

    public void httpSucc(String result, int id);

}
