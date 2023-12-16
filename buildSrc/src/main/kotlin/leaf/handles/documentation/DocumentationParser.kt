package leaf.handles.documentation

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URLClassLoader


class DocumentationParser : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.create("writeDocumentation", AnalyserTask::class.java)
    }
}

data class LuaFunction(
    val name: String,
    val description: String,
    val args: Map<String, String>,
    val returns: Map<String, String>
)

open class AnalyserTask : DefaultTask() {

    companion object {
        const val LUA_FUNCTION_ANNOTATION = "dan200.computercraft.api.lua.LuaFunction"
    }

    @TaskAction
    fun analyser() {
        val sourceSet = project.extensions.getByType(JavaPluginExtension::class.java).sourceSets.getByName("main")
        val urls = sourceSet.runtimeClasspath.files.map { it.toURI().toURL() }.toTypedArray()
        val classLoader = URLClassLoader(urls, this.javaClass.classLoader)

        val functions = sourceSet.output.classesDirs.flatMap { dir ->
            dir.walkTopDown().filter {
                it.isFile && it.name.endsWith(".class")
            }.map { file ->
                file.relativeTo(dir).path
                    .removeSuffix(".class")
                    .replace(File.separatorChar, '.')
            }.map { className ->
                classLoader.loadClass(className)
            }.flatMap {
                it.methods.toList()
            }.flatMap { method ->
                method.annotations.mapNotNull { annotation ->
                    if (annotation.annotationClass.qualifiedName == LUA_FUNCTION_ANNOTATION) {
                        LuaFunction(
                            method.name,
                            "description",
                            method.parameters.associate { it.name!! to it.type.name },
                            mapOf("return" to method.returnType.name)
                        )
                    } else null
                }
            }
        }

        functions.forEach {
            println("Function: ${it.name}")
        }
    }
}
