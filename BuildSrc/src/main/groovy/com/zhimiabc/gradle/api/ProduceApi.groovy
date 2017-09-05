package com.zhimiabc.gradle.api

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProduceApi implements Plugin<Project> {
    @Override
    void apply(Project project) {
        applyTask(project)
    }


    void applyTask(Project project) {
        project.afterEvaluate {
            println("produce api")
            def pluginName = project.name + "-api"
            File pluginDir = new File("./$pluginName")
            clearApiFiles(pluginDir)
            copyApiFiles(new File(project.name), pluginDir)
            buildGradle(pluginDir)
//            checkGradleSetting(project, pluginName, pluginDir.exists())
        }
    }

    void copyApiFiles(File originDir, File targetDir) {
        originDir.eachFile { file ->
            if (file.isDirectory()) {
                copyApiFiles(new File("$originDir/$file.name"), new File("$targetDir/$file.name"))
            } else {
                if (file.name.endsWith("api")) {
                    if (!targetDir.exists()) {
                        targetDir.mkdirs()
                    }
                    new File("$targetDir/$file.name") << file.text
                }
            }
        }
    }

    void buildGradle(File pluginFile) {
        if (pluginFile.exists()) {
            File buildGradle = new File("$pluginFile/build.gradle")
            buildGradle.write("apply plugin: 'java-library'")
        }
    }

    void clearApiFiles(File pluginFile) {
        pluginFile.deleteDir()
    }

    void checkGradleSetting(Project project, String pluginName, boolean exist) {
        File gradleSettingFile = new File("/settings.gradle")
        println(">>>>>>>>>>$gradleSettingFile.absolutePath")
        String strAppend = ", ':$pluginName'"
        if (exist) {
            if (!gradleSettingFile.text.contains(pluginName)) {
                gradleSettingFile.write(strAppend)
            }
        } else {
            if (gradleSettingFile.text.contains(pluginName)) {
                String originTxt = gradleSettingFile.text
                originTxt.replaceAll(strAppend, "")

                PrintWriter writer = new PrintWriter(gradleSettingFile)
                writer.print(originTxt)
                writer.close()
            }
        }
    }


}