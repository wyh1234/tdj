//
// Created by Administrator on 2018-08-30.
//

#include <jni.h>
#include <string>


//类型           相应的签名
//boolean        Z
//byte           B
//char           C
//short          S
//int            I
//long           J
//float          F
//double         D
//void           V
//object         L用/分隔包的完整类名：   Ljava/lang/String;
//Array          [签名          [I      [Ljava/lang/Object;
//Method         (参数1类型签名 参数2类型签名···)返回值类型签名


//using namespace std;
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1order(JNIEnv *env, jclass type) {
    const char *ss = "order/alipaySuccessNotify";
    return env->NewStringUTF(ss);
}
//using namespace std;
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1orderhb(JNIEnv *env, jclass type) {
    const char *ss = "order/huabeiPaySuccessNotify ";
    return env->NewStringUTF(ss);
}

//using namespace std;
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_supplierAnnalFee_1alipay(JNIEnv *env, jclass type) {
//    const char *ss = "supplierAnnalFee/alipay/notify";
    const char *ss = "payManage/alipay/notify";
    return env->NewStringUTF(ss);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1recharge(JNIEnv *env, jclass type) {
    const char *ss = "recharge/alipaySuccessNotify";
    return env->NewStringUTF(ss);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1rechargehb(JNIEnv *env, jclass type) {
    const char *ss = "recharge/huabeiPaySuccessNotify";
    return env->NewStringUTF(ss);
}
//wuhan
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipayId(JNIEnv *env, jclass type) {
    const char *ss = "2019042464254677";
    return env->NewStringUTF(ss);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1PRI(JNIEnv *env, jclass type) {
    const char *ss = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCLa42iN5371F04Q3koNg714gJqqQDFnOA7bHOJ6r7SSuWYkUFVJ7BKhD3CGuN3xlS8A7IfTF7JCN4xUv1bkLKTDjdFMxnRRO//a6oPCbN0j9p/NoYLwtjzlY8Sf6YkdNdRIu+8HM/h1W2PmkcU4mPD+b5fvZwx43MxuEV24v5038vo7VVaedQPexak5orEOaut75Qp9EGftWeH7ZiQcN5ba633a+VfTP4pu0zJFisjjgzoGi5yimaTnVd9YTb4oXhucrfxHQx5YqIiUN+/9O/KxhP0OqkIarjAOr85BRqdWdVoA9efh+e1ahyVrHeLRiRAZQf6WbkakeMOVEXHOLBTAgMBAAECggEAf8ZnfNcaHDVp5em4vUil92VWL6iKA5Id03g2f5y7nAF2YJgnV0LTh8egzidUd242NEXy1kpoyDgvYucbOfLoJmwt7WbC748NoXpr1fJfHKHGbzZqLZc6vCtCf4KrotfOihbJvg5t2xx+l9Cd+p7tLivR9O7SmspIxbWLPEUXTcZ6Mcxw2CEaz8EaspAIeO+6yNvwn9wFku6+/AMPtid7ZZyf6lFJGxalVImD9+zeu+CCy1tEHbhoP/4sSb20svY3kpCBJZJPA53M73ZYIxZyIRNGq0Wfk+pg/eKSvhizoTZt30b2YZzq3vAzID8Pqn8C2yijDUUk98z7mIVdr6iM+QKBgQDgV50Alfh3SW6bo/6gN+J36bWnM/4sIN0/JDSsQzCZKgCec+KYPjR0iLIgcgdPTC7Iz/97hYfXEvhW9wxz83UtkQv0byVrkKYrI53snhj96QCibYUD72s9LKDq1CcdgPLGKHmWWNGoA0aTakhJ/ImZoRUopnbdvB01aeM/evNCrwKBgQCfGB60JP0ft+T1owCh66wVvtGX+TpExrMEc6d7vIKucF3NjWk1nhB53MeVnltOjytoyJ5RUUNWZEchXppuAPH97dCWLLi2O7I++c8g+GTiRYSBfFwViUXWz9lMrtgXTFfslcX+zPYNP8nGF2NXyyMrVx3tV5frBRynYQj85lKlnQKBgQCFH1g55cQnvLpWvwy0NBU9xMAXNMIJm4LOGgTrh39KE+OEIw39+0OTNx5EJIXz5ygH320h9g2ycT5OFJEeKQSKgnyOknmpB4+jH6Jxqyr3WJEY0VZSDHsdNONUFYoUdtUZAyGLNq9KPI9foHy+8o0QTcbJdWYpk5vdezTvEV1SXwKBgCpsgCOBiY4ft4pZAR4sfiL+U123M/aLFcmmHY89YYuqoxjhEKEfmT0lxoL7S1XTj7vNY4pDpTUwYFdhpsSWs/EGrYyeOFM/7/QjITylgCFmcEMnKlcD1//ERSnRpgXwcbHA7p9XgHVgASo1qmQK40H3ZR+ZMAWXw2BO29q0gG1xAoGBANdTV5oubO3gtY2pqPz8hSJf5P9poCOm4rf4NxS9RTEpO5r/e1/EMErO7pSsBW28EwANbXNKZLoHsfs6yiq/3Z4ITOl70zumKOIKcLIw4yAMA4E+yd7ooAyyJwqpeH5OQcD4Hg02wPE0aC96gSCyTvxzkDcl66draJ9ElTI8fO5Q";
    return env->NewStringUTF(ss);
}
//xiangyang
extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipayId_1xy(JNIEnv *env, jclass type) {
    const char *ss = "2016061701527929";
    return env->NewStringUTF(ss);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_alipay_1PRI_1xy(JNIEnv *env, jclass type) {
    const char *ss = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7SlusWmiufbTOMlIawKSWteFMam2eZ8Zv2fjiDqwKGbbrk1Tzw3eArrPwnl1PkYdcEzFdKx3XZzeHXyFNLAz2u3OhULQCKEznBfcHvGaXaL3vctc0NtmS1PATqRlPDlNpZjYO1zshqaPQgCM97zq3NTyHTRw/vw6lVVE3eSBAYc/uwycRLEy41dMWEaO7tc68xjzX/+vxrkMU0NhWEeA59HA90EnZDTsDQTFMZhxPohGz6yb6q+/yYRBVwfoam4/Um/jyEa2zDIA09hLRs6suVgg1JAzAc6QnH6s+PaDql/cUNICzwSAaSRH0mTvYQWTg1Lw0n6U8CchY8mmvz+VdAgMBAAECggEAcJ0fThz2gKTZSHp9X9ShonUuSPJdgzfG245m1hYYX+JiWHe4RvIrMtbZHz85wBQzHav0rJ62TMSlYOJUdcCc8uZ3enG6Kunsg6TUinRAA55ZRlb5C05HuMAMsuWkcCKfCOsKahYEWhNUipPhnOXxmiOKh5hX+gR5W/RW51IPkGW4JUAWi7wYw2cLv/hWYjzard8Ltg0Uc8ocSOR5oTM39uWzdzdhk2eMIXRMJdWjOQiyE90eKiK14iqg4iEAayAcU2Wz0aDqXBDZWBlZ4MgexIiUozVkTIPa0BkZS3nIviIfAZT38+8BbZOBCCCvTZ6aTe6C/pEMNHjYTFEqmcCrQQKBgQDmYto0ljP5EaX2RB+ts1aty7xyefXtJWMIv4Pn4fPY9Rvhc4qjXwMe7AuEwpG0pr2mkqLJpvjiTzJNsB1PVBOD6lIW90wFzE4PujH574RjYDT8N2fHfWDG0MxyVUlH5mpmF/R63jBKYHggIwaNb7ts2ahrBcjjZHs6QxPT4s5oZQKBgQDQHO+JMx1nyEtSekSoq0GQU0rtRbK36nW23TOqI0uw53EbB68pLj6NYfSOUOINuy/V1mIxwOLuoWzfbzrCDtwWposPeOPMsOSucyJ3EYtCroWALOmh0wjWohZoAXPBT6ykl/h/6PvmoDoeWQ1gmsSEc8nxBzXLqvVPrDoxRLbtmQKBgA+/sirmGadtDWtIPOIi4EoBI2Cv9Z/AboSqktnHGGQ8XSuMOm3wkMYMXL02j79Jpq08WORwBZNy1n15DIfft3XNDs+DGC9NSAjLMZ9kQCiK6xRStUVtzK/Kli86pUfE+IECIjRD/AkZjxyjSE9i0+0c8+Zc/t6Mf9/7td9GIdYBAoGBAJ+uJAjkhGJ2J30QPZAhXvwLPMlTmi5NoqZ9kT+xN0l0O6+etMJnLi2zBD8SRnB8uNZdWRiNxFGO4IeLw8zUNADZJofnnPoYnE5SHxa3CmBOojZXv6ef0f9FfOWuf4/Kv0/HHy+dp3IsbIuOjpoaBkEOytq6SoeMFA9euYPGEom5AoGAULKpsWxNJCwQNS2m91FQ92EC5N1XpAwnPpQrjoqUbvqgIY+wBgfXSkBNkxCYmjjxQmjajwVe+haBvwGAZIic7Am8KoqyoeUoOyD5GLPVHHg4fdayKwDfIyOygpciEvUz2KxxhRHkoWeZ9U/9U5cGyJ+3XqS3845tfVcm2wguHjY=";
    return env->NewStringUTF(ss);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_tools_jni_JniMethod_wxpayId(JNIEnv *env, jclass type) {
    const char *ss = "wxf1c361fc73d52a29";
    return env->NewStringUTF(ss);
}