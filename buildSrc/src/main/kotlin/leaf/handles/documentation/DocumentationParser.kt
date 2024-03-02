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
    val description: String?,
    val args: Map<String, String>?,
    val returns: String?,
    val example: String?,
) {
    fun toMarkdown(): String {
        val builder = StringBuilder()

        builder.append("### `$name`\n\n")

        if (!description.isNullOrEmpty()) {
            builder.append("$description\n\n")
        }

        if (!args.isNullOrEmpty()) {
            builder.append("#### Arguments\n\n")
            args.forEach { (name, type) ->
                builder.append("* `$name` - `$type`\n")
            }
            builder.append("\n")
        }

        if (!returns.isNullOrEmpty()) {
            builder.append("#### Returns\n\n")
            builder.append("$returns\n")
        }

        if (!example.isNullOrEmpty()) {
            builder.append("#### Example\n\n")
            builder.append("$example\n")
        }

        return builder.toString()
    }
}

open class AnalyserTask : DefaultTask() {

    companion object {
        const val LUA_FUNCTION_ANNOTATION = "dan200.computercraft.api.lua.LuaFunction"
        const val HANDLES_FUNCTION_ANNOTATION = "leaf.handles.peripherals.HandlesFunction"
        const val HANDLES_PARAMETER_ANNOTATION = "leaf.handles.peripherals.HandlesParameter"
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
                        val handlesFunction = method.annotations.firstOrNull { it.annotationClass.qualifiedName == HANDLES_FUNCTION_ANNOTATION } as? Any

                        val description = handlesFunction?.javaClass?.getMethod("description")?.invoke(handlesFunction) as? String ?: ""
                        val returns = handlesFunction?.javaClass?.getMethod("returns")?.invoke(handlesFunction) as? String ?: ""
                        val example = handlesFunction?.javaClass?.getMethod("example")?.invoke(handlesFunction) as? String ?: ""

                        LuaFunction(
                            method.name,
                            description,
                            method.parameters.associate { parameter ->
                                val handlesParameter = parameter.annotations.firstOrNull { it.annotationClass.qualifiedName == HANDLES_PARAMETER_ANNOTATION } as? Any
                                val parameterName = handlesParameter?.javaClass?.getMethod("name")?.invoke(handlesParameter) as? String ?: parameter.name

                                parameterName to parameter.type.simpleName
                            },
                            returns,
                            example
                        )
                    } else null
                }
            }
        }

        val builder = StringBuilder()

        functions.forEach { function ->
            builder.append(function.toMarkdown())
        }

        val file = File(project.projectDir, "docs/FUNCTIONS.md")
        file.parentFile.mkdirs()

        file.writeText(builder.toString())

        println("Wrote ${functions.size} functions to ${file.absolutePath}")
    }
}
