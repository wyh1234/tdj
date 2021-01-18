package com.base.retrofit;


/**
 * Created by Administrator on 2016/11/23.
 */


public interface RetrofitApiHelp {

  /** 1, 当@GET或@POST注解的url为全路径时（可能和baseUrl不是一个域），会直接使用注解的url的域。
   2, 如果请求为post实现，那么最好传递参数时使用@Field、@FieldMap和@FormUrlEncoded。
   因为@Query和或QueryMap都是将参数拼接在url后面的，而@Field或@FieldMap传递的参数时放在请求体的。
   3, 使用@Path时，path对应的路径不能包含”/”，否则会将其转化为%2F。在遇到想动态的拼接多节url时，还是使用@Url吧。*/



/**    //用于访问zhy的信息
    http://192.168.1.102:8080/springmvc_users/user/zhy
//用于访问lmj的信息
    http://192.168.1.102:8080/springmvc_users/user/lmj

    @GET("{username}")
    Call<User> getUser(@Path("username") String username);

    Call<User> call = userBiz.getUser("zhy");*/





/**    http://baseurl/users?sortby=username
    http://baseurl/users?sortby=id

    @GET("users")
    Call<List<User>> getUsersBySort(@Query("sortby") String sort);*/



/**
 *一、get方式请求静态url地址
 复制代码
 Retrofit retrofit = new Retrofit.Builder()
 .baseUrl("https://api.github.com/")
 .build();
 public interface GitHubService {
 //无参数
 @GET("users/stven0king/repos")
 Call<List<Repo>> listRepos();
 //少数参数
 @GET("users/stven0king/repos")
 Call<List<Repo>> listRepos(@Query("time") long time);
 //参数较多
 @GET("users/stven0king/repos")
 Call<List<Repo>> listRepos(@QueryMap Map<String, String> params);
 }
 *
 *二、post方式请求静态url地址
 复制代码
 Retrofit retrofit = new Retrofit.Builder()
 .baseUrl("https://api.github.com/")
 .build()
 public interface GitHubService {
 //无参数
 @POST("users/stven0king/repos")
 Call<List<Repo>> listRepos();
 //少数参数
 @FormUrlEncoded
 @POST("users/stven0king/repos")
 Call<List<Repo>> listRepos(@Field("time") long time);
 //参数较多
 @FormUrlEncoded
 @POST("users/stven0king/repos")
 Call<List<Repo>> listRepos(@FieldMap Map<String, String> params);
 }
 复制代码

 @Field和@FieldMap可以结合在一起使用。

 另外是不是发现了比起@GET多了一个@FromUrlEncoded的注解。
 如果去掉@FromUrlEncoded在post请求中使用@Field和@FieldMap，
 那么程序会抛出java.lang.IllegalArgumentException: @Field parameters can only be used with form encoding.
 (parameter #1)的错误异常。如果将@FromUrlEncoded添加在@GET上面呢，同样的也会抛出java.lang.IllegalArgumentException:
 FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).的错误异常
 *
 *  三、半静态的url地址请求
 复制代码
 Retrofit retrofit = new Retrofit.Builder()
 .baseUrl("https://api.github.com/")
 .build()

 public interface GitHubService {
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);
}
 *
 *
 * 四、动态的url地址请求

Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .build()

    public interface GitHubService {
        @GET
        Call<List<Repo>> listRepos(@Url String user);
    }

 最后的最后，一定要注意用json上传，@Body中千万不能写String类型，否则会导致请求体构造错误（json格式不正确，多出\），正确写法如下：

 @POST("FundPaperTrade/AppUserLogin")
 Observable<Response> getTransData(@Body TestBean str);

 */





}
