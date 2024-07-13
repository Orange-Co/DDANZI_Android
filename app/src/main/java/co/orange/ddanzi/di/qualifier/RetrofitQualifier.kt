package co.orange.ddanzi.di.qualifier

import javax.inject.Qualifier

object RetrofitQualifier {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class JWT

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NOTOKEN

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DEVICE
}
