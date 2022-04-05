LOCAL_PATH := $(call my-dir)

#TODO: not include empty tdjsonjni
include $(CLEAR_VARS)
LOCAL_MODULE    := tdjson
LOCAL_SRC_FILES := /home/oleg/IdeaProjects/learn/td/built/tdlib/android/$(TARGET_ARCH_ABI)/lib/libtdjson.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := tdjsonjni
LOCAL_SHARED_LIBRARIES  := tdjson
include $(BUILD_SHARED_LIBRARY)
