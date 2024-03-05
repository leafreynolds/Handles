package leaf.handles.documentation

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
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
            builder.append("```lua\n$example\n```\n\n")
        }

        builder.append("---\n\n")

        return builder.toString()
    }
}

data class LuaOSEvent(
        val methodName: String,
        val eventName: String?,
        val description: String?,
        val example: String?,
) {
    fun toMarkdown(): String {
        val builder = StringBuilder()

        if (!eventName.isNullOrEmpty()) {
            builder.append("### `$eventName`\n\n")
        } else {
            builder.append("### `$methodName`\n\n")
        }

        if (!description.isNullOrEmpty()) {
            builder.append("$description\n\n")
        }

        if (!example.isNullOrEmpty()) {
            builder.append("#### Example\n\n")
            builder.append("```lua\n$example\n```\n\n")
        }

        builder.append("---\n\n")

        return builder.toString()
    }
}

open class AnalyserTask : DefaultTask() {

    companion object {
        const val LUA_FUNCTION_ANNOTATION = "dan200.computercraft.api.lua.LuaFunction"
        const val HANDLES_FUNCTION_ANNOTATION = "leaf.handles.peripherals.HandlesFunction"
        const val HANDLES_PARAMETER_ANNOTATION = "leaf.handles.peripherals.HandlesParameter"
        const val HANDLES_OS_EVENT_ANNOTATION = "leaf.handles.peripherals.HandlesOSEvent"
    }

    @TaskAction
    fun analyser() {
        val sourceSet = project.extensions.getByType(JavaPluginExtension::class.java).sourceSets.getByName("main")
        val urls = sourceSet.runtimeClasspath.files.map { it.toURI().toURL() }.toTypedArray()
        val classLoader = URLClassLoader(urls, this.javaClass.classLoader)

        val builder = StringBuilder()


        val functions = getLuaFunctions(sourceSet, classLoader)


        builder.append("## Functions:\n")

        functions.forEach { function ->
            builder.append(function.toMarkdown())
        }

        val functionsFile = File(project.projectDir, "docs/FUNCTIONS.md")
        functionsFile.parentFile.mkdirs()

        functionsFile.writeText(builder.toString())

        println("Wrote ${functions.size} functions to ${functionsFile.absolutePath}")

        builder.clear()

        val luaOSEvents = getLuaOSEvents(sourceSet, classLoader)

        builder.append("## Events:\n")

        luaOSEvents.forEach { function ->
            builder.append(function.toMarkdown())
        }

        val osEventsFile = File(project.projectDir, "docs/EVENTS.md")
        osEventsFile.parentFile.mkdirs()

        osEventsFile.writeText(builder.toString())

        println("Wrote ${luaOSEvents.size} functions to ${osEventsFile.absolutePath}")

    }

    private fun getLuaFunctions(sourceSet: SourceSet, classLoader: URLClassLoader) =
            sourceSet.output.classesDirs.flatMap { dir ->
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

                            val description = handlesFunction?.javaClass?.getMethod("description")?.invoke(handlesFunction) as? String
                                    ?: ""
                            val returns = handlesFunction?.javaClass?.getMethod("returns")?.invoke(handlesFunction) as? String
                                    ?: ""
                            val example = handlesFunction?.javaClass?.getMethod("example")?.invoke(handlesFunction) as? String
                                    ?: ""

                            LuaFunction(
                                    method.name,
                                    description,
                                    method.parameters.associate { parameter ->
                                        val handlesParameter = parameter.annotations.firstOrNull { it.annotationClass.qualifiedName == HANDLES_PARAMETER_ANNOTATION } as? Any
                                        val parameterName = handlesParameter?.javaClass?.getMethod("name")?.invoke(handlesParameter) as? String
                                                ?: parameter.name

                                        parameterName to parameter.type.simpleName
                                    },
                                    returns,
                                    example
                            )
                        } else null
                    }
                }
            }


    private fun getLuaOSEvents(sourceSet: SourceSet, classLoader: URLClassLoader) =
            sourceSet.output.classesDirs.flatMap { dir ->
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
                        if (annotation.annotationClass.qualifiedName == HANDLES_OS_EVENT_ANNOTATION) {
                            val handlesOSEventFunction = method.annotations.firstOrNull { it.annotationClass.qualifiedName == HANDLES_OS_EVENT_ANNOTATION } as? Any

                            val eventName = handlesOSEventFunction?.javaClass?.getMethod("eventName")?.invoke(handlesOSEventFunction) as? String
                                    ?: ""
                            val description = handlesOSEventFunction?.javaClass?.getMethod("description")?.invoke(handlesOSEventFunction) as? String
                                    ?: ""
                            val example = handlesOSEventFunction?.javaClass?.getMethod("example")?.invoke(handlesOSEventFunction) as? String
                                    ?: ""

                            LuaOSEvent(
                                    method.name,
                                    eventName,
                                    description,
                                    example
                            )
                        } else null
                    }
                }
            }

}
