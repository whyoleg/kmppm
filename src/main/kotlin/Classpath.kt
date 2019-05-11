object Classpath {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlin}"
    const val atomicfu = "org.jetbrains.kotlinx:atomicfu-gradle-plugin:${Version.atomicfu}"

    const val jib = "gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:${Version.jib}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Version.detekt}"
    const val gradleUpdate = "com.github.ben-manes:gradle-versions-plugin:${Version.gradleUpdate}"

    const val pgEmbedded = "com.opentable.components:otj-pg-embedded:${Version.pgEmbedded}"
    const val postgresql = "org.postgresql:postgresql:${Version.postgresql}"
    const val vertxJooq = "io.github.jklingsporn:vertx-jooq-generate:${Version.vertxJooq}"

    const val xml = "org.redundent:kotlin-xml-builder:${Version.xml}"
    const val jaxbCore = "com.sun.xml.bind:jaxb-core:${Version.jaxbCore}"
    const val jaxbImpl = "com.sun.xml.bind:jaxb-impl:${Version.jaxbImpl}"
    const val activation = "javax.activation:activation:${Version.activation}"

    const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Version.sqlDelight}"
}