#include <td/telegram/td_json_client.h>

#include <jni.h>

extern "C" {

JNIEXPORT jlong JNICALL Java_dev_whyoleg_tdlib_kotlin_JsonClient_create(JNIEnv *env, jclass clazz) {
  return reinterpret_cast<jlong>(td_json_client_create());
}

JNIEXPORT void JNICALL Java_dev_whyoleg_tdlib_kotlin_JsonClient_send(JNIEnv *env, jclass clazz, jlong client, jstring request) {
  td_json_client_send(reinterpret_cast<void *>(client), td::jni::from_jstring(env, request).c_str());
}

JNIEXPORT jstring JNICALL Java_dev_whyoleg_tdlib_kotlin_JsonClient_receive(JNIEnv *env, jclass clazz, jlong client, jdouble timeout) {
  auto result = td_json_client_receive(reinterpret_cast<void *>(client), timeout);
  if (result == nullptr) {
    return nullptr;
  }
  return td::jni::to_jstring(env, result);
}

JNIEXPORT jstring JNICALL Java_dev_whyoleg_tdlib_kotlin_JsonClient_execute(JNIEnv *env, jclass clazz, jlong client, jstring request) {
  auto result = td_json_client_execute(reinterpret_cast<void *>(client), td::jni::from_jstring(env, request).c_str());
  if (result == nullptr) {
    return nullptr;
  }
  return td::jni::to_jstring(env, result);
}

JNIEXPORT void JNICALL Java_dev_whyoleg_tdlib_kotlin_JsonClient_destroy(JNIEnv *env, jclass clazz, jlong client) {
  td_json_client_destroy(reinterpret_cast<void *>(client));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
  return JNI_VERSION_1_6;
}

}
