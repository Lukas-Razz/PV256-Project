//
// Created by lukas on 01.10.2017.
//

#include <jni.h>

extern "C" {
    JNIEXPORT jstring JNICALL
    Java_cz_muni_fi_pv256_movio2_uco_1410034_App_getApiKey(JNIEnv *env, jobject instance) {

     return env -> NewStringUTF("be9ea8ac0abe3b51e1a91c66fbcf2241");
    }
}