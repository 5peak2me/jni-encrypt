LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# 对应打包成函数库的名字
LOCAL_MODULE    := md5
# 对应c代码的文件
LOCAL_SRC_FILES := md5_util.c md5.c
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)