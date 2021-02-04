# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

##---------------Begin: proguard configuration for SQLCipher  ----------
#noinspection ShrinkerUnresolvedReference
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

-dontwarn org.conscrypt.ConscryptHostnameVerifier

-keepclassmembers,allowobfuscation class com.the.movie.db.source.remote.response.** { *; }