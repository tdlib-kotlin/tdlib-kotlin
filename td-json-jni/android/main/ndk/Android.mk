LOCAL_PATH := $(call my-dir)
$(info $(LOCAL_PATH))
include $(CLEAR_VARS)
LOCAL_MODULE    := tdjson
LOCAL_SRC_FILES := $(LOCAL_PATH)/../../../../../../built/tdlib/android/$(TARGET_ARCH_ABI)/lib/libtdjson.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    		:= tdjsonjni
LOCAL_SRC_FILES 		:= $(LOCAL_PATH)/../../../common/main/cpp/td_json_jni.cpp
LOCAL_C_INCLUDES		:= $(LOCAL_PATH)/../../../../../../built/tdlib/android/$(TARGET_ARCH_ABI)/include
LOCAL_SHARED_LIBRARIES	:= tdjson
include $(BUILD_SHARED_LIBRARY)
