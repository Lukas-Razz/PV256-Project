LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := apikeys
LOCAL_SRC_FILES := apikeys.c

include $(BUILD_SHARED_LIBRARY)