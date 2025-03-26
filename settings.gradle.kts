pluginManagement {
    repositories {
        google {
            maven { url=uri( "https://maven.aliyun.com/repository/central") }
            maven { url=uri( "https://maven.aliyun.com/repository/public") }
            maven { url=uri( "https://maven.aliyun.com/repository/google") }
            maven { url=uri( "https://maven.aliyun.com/repository/gradle-plugin") }
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url=uri( "https://maven.aliyun.com/repository/central") }
        maven { url=uri( "https://maven.aliyun.com/repository/public") }
        maven { url=uri( "https://maven.aliyun.com/repository/google") }
        maven { url=uri( "https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
    }
}

rootProject.name = "MiNotes"
include(":app")
