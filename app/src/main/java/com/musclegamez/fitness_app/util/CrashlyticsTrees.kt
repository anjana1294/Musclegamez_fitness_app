package com.musclegamez.fitness_app.util
import timber.log.Timber

 class CrashlyticsTrees : Timber.Tree() {
    companion object {
        const val CRASHLYTICS_KEY_PRIORITY = "priority"
        const val CRASHLYTICS_KEY_TAG = "tag"
        const val CRASHLYTICS_KEY_MESSAGE = "message"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        /*    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }
            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (message != null) {
                Crashlytics.log(message)
            } else {
                Crashlytics.logException(t);
            }*/
        }


}