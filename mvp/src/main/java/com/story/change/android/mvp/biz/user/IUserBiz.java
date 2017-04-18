package com.story.change.android.mvp.biz.user;

import com.story.change.android.mvp.bean.base.ResponseBase;
import com.story.change.android.mvp.bean.user.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by story on 2017/4/12 0012 上午 10:57.
 * 定义用户相关接口
 */
public interface IUserBiz {

    @FormUrlEncoded
    @POST("ChangeWeb/user/getPhoneCheck")
    Observable<ResponseBase<String>> userGetCheck(@Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("ChangeWeb/user/loginWithPwd")
    Observable<ResponseBase<User>> userLoginByPwd(@Field("userName") String userName, @Field("userPwd") String pwd);

    @FormUrlEncoded
    @POST("ChangeWeb/user/loginWithCheck")
    Observable<ResponseBase<User>> userLoginByPhone(@Field("userPhone") String userPhone, @Field("userCheck") String userCheck);

    @FormUrlEncoded
    @POST("ChangeWeb/user/register")
    Observable<ResponseBase<User>> userRegister(@Field("userPhone") String userPhone, @Field("userCheck") String userCheck);
}
