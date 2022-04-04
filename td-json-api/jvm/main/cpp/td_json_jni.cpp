#include <td/telegram/td_json_client.h>

#include <jni.h>

extern "C" {

JNIEXPORT jint JNICALL Java_dev_whyoleg_td_json_TdJsonJni_createClientId(JNIEnv *env, jclass clazz) {
    return td_create_client_id();
}

JNIEXPORT void JNICALL Java_dev_whyoleg_td_json_TdJsonJni_send(JNIEnv *env, jclass clazz, jint client_id, jstring request) {
    const char *nativeString = env->GetStringUTFChars(request, nullptr);
    td_send(client_id, nativeString);
    env->ReleaseStringUTFChars(request, nativeString);
}

JNIEXPORT jstring JNICALL Java_dev_whyoleg_td_json_TdJsonJni_receive(JNIEnv *env, jclass clazz, jdouble timeout) {
    auto result = td_receive(timeout);
    if (result == nullptr) return nullptr;
    return env->NewStringUTF(result);
}

JNIEXPORT jstring JNICALL Java_dev_whyoleg_td_json_TdJsonJni_execute(JNIEnv *env, jclass clazz, jstring request) {
    const char *nativeString = env->GetStringUTFChars(request, nullptr);
    auto result = td_execute(nativeString);
    env->ReleaseStringUTFChars(request, nativeString);
    if (result == nullptr) return nullptr;
    return env->NewStringUTF(result);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
  return JNI_VERSION_1_6;
}

}
