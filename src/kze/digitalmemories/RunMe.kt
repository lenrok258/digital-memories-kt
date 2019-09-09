package kze.digitalmemories

import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import java.io.IOException
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import javax.swing.text.StyleConstants.setIcon
import javax.swing.JLabel
import java.awt.FlowLayout
import java.net.URL
import javax.swing.ImageIcon
import java.awt.BorderLayout
import com.sun.javafx.robot.impl.FXRobotHelper.getChildren
import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.stage.Screen.getPrimary
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.MediaView
import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.Screen
import java.awt.Color


class ImagePanel(isDoubleBuffered: Boolean) : JPanel(isDoubleBuffered) {

    private var image: BufferedImage? = null

    fun ImagePanel() {
        try {
            image =
                ImageIO.read(File("/home/kornel/private/projects/digital-memories-kt/src/kze/digitalmemories/test-image.jpg"))
            layout = null
            setBounds(0, 0, 100, 100)

        } catch (ex: IOException) {
            // handle exception...
        }

    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(image, 0, 0, this) // see javadoc for more info on the parameters
    }
}


fun main() {

    val frame = JFrame()
    frame.layout = FlowLayout()
    frame.setSize(500, 500)
    frame.setLocationRelativeTo(null)

    // Image
    /*val img =
        ImageIO.read(URL("file:///home/kornel/private/projects/digital-memories-kt/src/kze/digitalmemories/test-image.jpg"))
    val icon = ImageIcon(img)
    val lbl = JLabel()
    lbl.icon = icon
    frame.add(lbl)*/

    // Video
    val videoPanel = JPanel(true)

    videoPanel.background = Color.PINK
    videoPanel.setBounds(0, 0, 100, 100)
    getVideo(videoPanel)

    frame.add(videoPanel)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

}

private fun getVideo(videoPanel: JPanel) {
    val VFXPanel = JFXPanel()

    val video_source = File("/home/kornel/private/projects/digital-memories-kt/src/kze/digitalmemories/test-video.mp4")
    val m = Media(video_source.toURI().toString())
    val player = MediaPlayer(m)
    val viewer = MediaView(player)

    val root = StackPane()
    val scene = Scene(root)

    // center video position
    val screen = Screen.getPrimary().getVisualBounds()
    viewer.x = (screen.getWidth() - videoPanel.getWidth()) / 2
    viewer.y = (screen.getHeight() - videoPanel.getHeight()) / 2

    // resize video based on screen size
    val width = viewer.fitWidthProperty()
    val height = viewer.fitHeightProperty()
    width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"))
    height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"))
    viewer.isPreserveRatio = true

    // add video to stackpane
    root.children.add(viewer)

    VFXPanel.scene = scene
    player.play();
    videoPanel.setLayout(BorderLayout())
    videoPanel.add(VFXPanel, BorderLayout.CENTER)
}
