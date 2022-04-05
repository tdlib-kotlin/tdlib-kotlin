LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := tdjsonapijni
LOCAL_SRC_FILES := $(LOCAL_PATH)/../td-json-api-jni/src/td_json_api_jni.cpp
LOCAL_CFLAGS += -I/home/oleg/IdeaProjects/learn/td/built/tdlib/android/$(TARGET_ARCH_ABI)/include
LOCAL_LDFLAGS += -L/home/oleg/IdeaProjects/learn/td/built/tdlib/android/$(TARGET_ARCH_ABI)/lib -ltdjson
include $(BUILD_SHARED_LIBRARY)
