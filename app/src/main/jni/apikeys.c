//
// Created by lukas on 01.10.2017.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_cz_muni_fi_pv256_movio2_uco_1410034_App_getApiKey(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTllYThhYzBhYmUzYjUxZTFhOTFjNjZmYmNmMjI0MSIsInN1YiI6IjU5ZDE0ZTZmYzNhMzY4NzkxNjAyNjk0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1-8XsPn39i3G5XdFO_d3inYeNMt_PjZH5RrOPZUnH3w");
}