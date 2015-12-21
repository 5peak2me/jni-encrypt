//
// Created by J!nl!n on 15/12/19.
//

#include <stdlib.h>
#include "com_jinlin_encryptutils_base64_Base64.h"

#include <android/log.h> // 这个是输出LOG所用到的函数所在的路径

#define LOG_TAG    "BASE64" // 这个是自定义的LOG的标识
#undef LOG // 取消默认的LOG

#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__) // 定义LOG类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__) // 定义LOG类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__) // 定义LOG类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__) // 定义LOG类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG,__VA_ARGS__) // 定义LOG类型

const char base[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

char *base64_encode(const char *data, int data_len);

char *base64_decode(const char *data, int data_len);

static char find_pos(char ch);

// 把java的字符串转换成c的字符串,使用反射
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    // 1:先找到字节码文件
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    // 2:通过字节码文件找到方法ID
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    // 3:通过方法id，调用方法
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode); // String .getByte("GB2312");
    // 4:得到数据的长度
    jsize alen = env->GetArrayLength(barr);
    // 5：得到数据的首地址
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    // 6:得到C语言的字符串
    if (alen > 0) {
        rtn = (char *) malloc((size_t) (alen + 1)); //"\0"
        memcpy(rtn, ba, (size_t) alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

/*
 * Class:     com_jinlin_encryptutils_base64_Base64
 * Method:    encode
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_jinlin_encryptutils_base64_Base64_encode
        (JNIEnv *env, jobject clazz, jstring string) {
    // 此时的t里面有了jstring的内容
    char *cstr = Jstring2CStr(env, string);
    LOGD("original: %s", cstr);
    int len = (int) strlen(cstr);
    char *enc = base64_encode(cstr, len);
    LOGD("encoded : %s", enc);
    return env->NewStringUTF(enc);
}

/*
 * Class:     com_jinlin_encryptutils_base64_Base64
 * Method:    decode
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_jinlin_encryptutils_base64_Base64_decode
        (JNIEnv *env, jobject clazz, jstring string) {
    // 先将jstring转换成char*
    char *cstr = (char *) env->GetStringUTFChars(string, 0);
    LOGD("original: %s", cstr);
    int len = (int) strlen(cstr);
    char *dec = base64_decode(cstr, len);
    LOGD("decoded : %s", dec);
    // 将base64编码后的char转换成jstring返回给java层
    return env->NewStringUTF(dec);
}

/* */
char *base64_encode(const char *data, int data_len) {
    //int data_len = strlen(data);
    int prepare = 0;
    int ret_len;
    int temp = 0;
    char *ret = NULL;
    char *f = NULL;
    int tmp = 0;
    char changed[4];
    int i = 0;
    ret_len = data_len / 3;
    temp = data_len % 3;
    if (temp > 0) {
        ret_len += 1;
    }
    ret_len = ret_len * 4 + 1;
    ret = (char *) malloc(ret_len);

    if (ret == NULL) {
        LOGD("No enough memory.\n");
        exit(0);
    }
    memset(ret, 0, ret_len);
    f = ret;
    while (tmp < data_len) {
        temp = 0;
        prepare = 0;
        memset(changed, '\0', 4);
        while (temp < 3) {
            //printf("tmp = %d\n", tmp);
            if (tmp >= data_len) {
                break;
            }
            prepare = ((prepare << 8) | (data[tmp] & 0xFF));
            tmp++;
            temp++;
        }
        prepare = (prepare << ((3 - temp) * 8));
        //printf("before for : temp = %d, prepare = %d\n", temp, prepare);
        for (i = 0; i < 4; i++) {
            if (temp < i) {
                changed[i] = 0x40;
            }
            else {
                changed[i] = (prepare >> ((3 - i) * 6)) & 0x3F;
            }
            *f = base[changed[i]];
            //printf("%.2X", changed[i]);
            f++;
        }
    }
    *f = '\0';

    return ret;

}

/* */
static char find_pos(char ch) {
    char *ptr = (char *) strrchr(base, ch);//the last position (the only) in base[]
    return (ptr - base);
}

/* */
char *base64_decode(const char *data, int data_len) {
    int ret_len = (data_len / 4) * 3;
    int equal_count = 0;
    char *ret = NULL;
    char *f = NULL;
    int tmp = 0;
    int temp = 0;
    char need[3];
    int prepare = 0;
    int i = 0;
    if (*(data + data_len - 1) == '=') {
        equal_count += 1;
    }
    if (*(data + data_len - 2) == '=') {
        equal_count += 1;
    }
    if (*(data + data_len - 3) == '=') {//seems impossible
        equal_count += 1;
    }
    switch (equal_count) {
        case 0:
            ret_len += 4;//3 + 1 [1 for NULL]
            break;
        case 1:
            ret_len += 4;//Ceil((6*3)/8)+1
            break;
        case 2:
            ret_len += 3;//Ceil((6*2)/8)+1
            break;
        case 3:
            ret_len += 2;//Ceil((6*1)/8)+1
            break;
    }
    ret = (char *) malloc(ret_len);
    if (ret == NULL) {
        LOGD("No enough memory.\n");
        exit(0);
    }
    memset(ret, 0, ret_len);
    f = ret;
    while (tmp < (data_len - equal_count)) {
        temp = 0;
        prepare = 0;
        memset(need, 0, 4);
        while (temp < 4) {
            if (tmp >= (data_len - equal_count)) {
                break;
            }
            prepare = (prepare << 6) | (find_pos(data[tmp]));
            temp++;
            tmp++;
        }
        prepare = prepare << ((4 - temp) * 6);
        for (i = 0; i < 3; i++) {
            if (i == temp) {
                break;
            }
            *f = (char) ((prepare >> ((2 - i) * 8)) & 0xFF);
            f++;
        }
    }
    *f = '\0';
    return ret;
}