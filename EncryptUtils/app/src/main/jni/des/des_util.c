//
// Created by J!nl!n on 15/12/21.
//
#include "com_jinlin_encryptutils_des_DES.h"
#include <stdio.h>
#include "des.h"
#include <malloc.h>
#include <string.h>

JNIEXPORT jbyteArray JNICALL Java_com_jinlin_encryptutils_des_DES_desCrypt(JNIEnv *env, jobject instance,
                                                                           jbyteArray data_,
                                                                           jbyteArray key_, jint flag) {
    jbyte *data = (*env)->GetByteArrayElements(env, data_, NULL);
    jbyte *key = (*env)->GetByteArrayElements(env, key_, NULL);
    int datalength = (*env)->GetArrayLength(env, data_);
    int keylength = (*env)->GetArrayLength(env, key_);
    if (keylength != 8) {
        return 0;
    }
    LOGI("data:%s, length:%d", data, datalength);
    char *data_block = (char *) malloc(8);
    char *process_block = (char *) malloc(8);
    int number = datalength / 8 + (flag ? 1 : 0);
    char *trans_data = (char *) malloc(number * 8);
    int size = 0;
    memset(trans_data, 0, number * 8);
    make_SubKey(key);
    for (int i = 0; i < number; ++i) {
        memset(data_block, 0, 8);
        if ((i + 1) * 8 <= datalength) {
            memmove(data_block, data + i * 8, 8);
        }
        else {
            memmove(data_block, data + i * 8, datalength - i * 8);
        }
        if (i != number - 1) {
            Des_edCryption(data_block, process_block, flag);
            memmove(trans_data + i * 8, process_block, 8);
            size += 8;
        }
        else if (flag) {
            int padding = 8 - datalength % 8;
            memset(data_block + 8 - padding, padding, padding);
            Des_edCryption(data_block, process_block, flag);
            memmove(trans_data + i * 8, process_block, 8);
            size += 8;
        }
        else {
            Des_edCryption(data_block, process_block, flag);
            int padding = process_block[7];
            LOGI("padding:%d", padding);
            if (padding <= 8) {
                memmove(trans_data + i * 8, process_block, 8 - padding);
                size += 8 - padding;
            }
        }
    }
    jbyteArray jba = (*env)->NewByteArray(env, size);
    LOGI("trans:%s,size:%d", trans_data, size);
    (*env)->SetByteArrayRegion(env, jba, 0, size, trans_data);
    free(process_block);
    free(data_block);
    free(trans_data);

    (*env)->ReleaseByteArrayElements(env, data_, data, 0);
    (*env)->ReleaseByteArrayElements(env, key_, key, 0);
    return jba;
}