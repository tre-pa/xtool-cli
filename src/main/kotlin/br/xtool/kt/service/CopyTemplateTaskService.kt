package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.CopyTemplateTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.TaskRepresentation
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import org.springframework.beans.factory.annotation.Autowired
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence


@TaskService("copy-template")
class CopyTemplateTaskService : AbstractTaskService() {

    @Autowired
    lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, component: ComponentRepresentation, task: TaskRepresentation) {
        val wTask = task as CopyTemplateTask
        log("dest: ${workspaceContext.workspace.path.resolve(ctx.destination)}\n")
        val vars = wTask.args.vars.mapValues { if(it.value is String) ctx.parse(it.value as String) else it.value }
        val velocityContext = VelocityContext(vars)
        val ve = getVelocityEngine(component.tplPath, component.tplPartialsPath)
        Files.walk(component.tplPath.resolve(wTask.args.src))
                .asSequence()
                .filter { Files.isRegularFile(it) }
                .forEach { copyOrTemplatize(ctx, component.tplPath, it, ve, velocityContext) }
    }

    /**
     * Retorna a engine Velocity do tipo File Resource.
     * Os paths
     */
    fun getVelocityEngine(mainTplPath: Path, partialsTplPath: Path): VelocityEngine {
        val ve = VelocityEngine()
        ve.setProperty("resource.loader", "file")
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "${mainTplPath},${partialsTplPath}")
        ve.init()
        return ve
    }

    fun copyOrTemplatize(ctx: ComponentExecutionContext, tplPath: Path, file: Path, ve: VelocityEngine, velocityContext: VelocityContext): Unit {
        if (file.toString().endsWith(".vm")) {
            val tpl = tplPath.relativize(file).toString()
            val t = ve.getTemplate(tpl)
            val writer = StringWriter()
            t.merge(velocityContext, writer)
            val finalPath = Paths.get("${workspaceContext.workspace.path.resolve(ctx.destination).resolve(tpl.toString().removeSuffix(".vm"))}")
            createFile(finalPath, writer.toString())
            log("${tpl} -> @|green,bold ${finalPath} |@")
        }
    }

    fun createFile(finalPath: Path, content: String) {
        if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent())
        val os = Files.newOutputStream(finalPath)
        os.write(content.toByteArray())
        os.flush()
        os.close()
    }
}