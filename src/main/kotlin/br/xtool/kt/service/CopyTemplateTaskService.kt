package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.ComponentRepresentation
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import org.springframework.beans.factory.annotation.Autowired
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path


@TaskService("copy-template")
class CopyTemplateTaskService : AbstractTaskService() {

    @Autowired
    lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, component: ComponentRepresentation, task: TaskRepresentation) {
//        val wTask = task as CopyTemplateTask
//        logHeader("src","${component.tplPath}")
//        logHeader("dest","${workspaceContext.workspace.path.resolve(ctx.destination)}")
//        val vars = wTask.args.vars.mapValues { if(it.value is String) ctx.parse(it.value as String) else it.value }
//        val velocityContext = VelocityContext(vars)
//        val ve = getVelocityEngine(component.tplPath, component.tplPartialsPath)
//        Files.walk(component.tplPath.resolve(wTask.args.src))
//                .asSequence()
//                .filter { Files.isRegularFile(it) }
//                .forEach { copyOrTemplatize(ctx, component.tplPath, it, ve, velocityContext) }
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

    private fun copyOrTemplatize(ctx: ComponentExecutionContext, tplPath: Path, file: Path, ve: VelocityEngine, velocityContext: VelocityContext): Unit {
//        if (file.toString().endsWith(".vm")) {
//            val tpl = tplPath.relativize(file).toString()
//            val t = ve.getTemplate(tpl)
//            val writer = StringWriter()
//            t.merge(velocityContext, writer)
//            val finalTpl = finalTpl(tpl, ve, velocityContext)
//            val finalPath = Paths.get("${workspaceContext.workspace.path.resolve(ctx.destination).resolve(finalTpl.removeSuffix(".vm"))}")
//            createFile(finalPath, writer.toString().toByteArray(StandardCharsets.UTF_8))
//            log("${tpl} -> @|green,bold ${finalPath} |@")
//            return
//        }
//        val tpl = tplPath.relativize(file).toString()
//        val finalTpl = finalTpl(tpl, ve, velocityContext)
//        val finalPath = Paths.get("${workspaceContext.workspace.path.resolve(ctx.destination).resolve(finalTpl)}")
//        createFile(finalPath, Files.readAllBytes(file))
//        log("${tpl} -> @|green,bold ${finalPath} |@")
    }

    private fun finalTpl(file: String, ve: VelocityEngine, velocityContext: VelocityContext): String {
        val stringWriter = StringWriter()
//        ve.evaluate(velocityContext, stringWriter, String(), file)
        return stringWriter.toString()
    }

    fun createFile(finalPath: Path, content: ByteArray) {
        if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent())
        val os = Files.newOutputStream(finalPath)
        os.write(content)
        os.flush()
        os.close()
    }
}