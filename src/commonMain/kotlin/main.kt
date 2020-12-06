import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.uiTextButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.format.*
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing
import org.jbox2d.dynamics.BodyType
import kotlin.reflect.KClass

suspend fun main() = Korge(Korge.Config(
	module = MainModule
))

object MainModule : Module() {
	override val mainScene: KClass<out Scene> = FirstScene::class
	override val title: String = "Sample"
	override val bgcolor: RGBA = Colors.AQUAMARINE
	override val size: SizeInt = SizeInt.invoke(x = 640, y = 480)
	override val windowSize: SizeInt = SizeInt.invoke(x = 640, y = 480)
	override val fullscreen: Boolean = false
	override val scaleMode: ScaleMode = ScaleMode.SHOW_ALL

	override suspend fun AsyncInjector.configure() {
		mapPrototype { FirstScene() }
		mapPrototype { SecondScene() }
	}
}

class FirstScene : Scene() {
	override suspend fun Container.sceneInit() {
		addItems()
		val restartButton = uiTextButton (text = "RESTART") {
			onClick {
				// Setting scene twice makes box2d bodies retain in some way
				sceneContainer.pushTo<FirstScene>()
				sceneContainer.pushTo<FirstScene>()
			}
		}
		uiTextButton (text = "SECOND SCENE") {
			onClick {
				// Setting scene twice makes box2d bodies retain in some way
				sceneContainer.pushTo<SecondScene>()
				sceneContainer.pushTo<SecondScene>()
			}
		}.alignLeftToRightOf(restartButton)
	}
}

class SecondScene : Scene() {
	override suspend fun Container.sceneInit() {
		addItems()
		val restartButton = uiTextButton (text = "RESTART") {
			onClick {
				// Setting scene twice makes box2d bodies retain in some way
				sceneContainer.pushTo<SecondScene>()
				sceneContainer.pushTo<SecondScene>()
			}
		}
		uiTextButton (text = "FIRST SCENE") {
			onClick {
				// Setting scene twice makes box2d bodies retain in some way
				sceneContainer.pushTo<FirstScene>()
				sceneContainer.pushTo<FirstScene>()
			}
		}.alignLeftToRightOf(restartButton)
	}
}

fun Container.addItems() {
	solidRect(20, 20, Colors.RED).position(100, 100).centered.rotation(30.degrees).registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 0.01)
	solidRect(20, 20, Colors.RED).position(109, 75).centered.registerBodyWithFixture(type = BodyType.DYNAMIC)
	solidRect(20, 20, Colors.RED).position(93, 50).centered.rotation((-15).degrees).registerBodyWithFixture(type = BodyType.DYNAMIC)
	solidRect(400, 100, Colors.WHITE).position(100, 300).centered.registerBodyWithFixture(type = BodyType.STATIC, friction = 0.2)
}
