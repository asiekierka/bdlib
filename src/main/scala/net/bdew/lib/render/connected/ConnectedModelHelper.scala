package net.bdew.lib.render.connected

import net.bdew.lib.render.primitive._
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.Direction

object ConnectedModelHelper {
  val edgeWidth: Float = 1 / 16F
  val offs: Float = 0.001F

  object Corner extends Enumeration {
    // Edges and Corners - Top/Bottom Left/Right
    // ALL is full face
    val T, TR, R, BR, B, BL, L, TL, ALL = Value
  }

  case class Rect(x1: Float, y1: Float, x2: Float, y2: Float) {
    def corners: List4[UV] = List4(UV(x1, y1), UV(x2, y1), UV(x2, y2), UV(x1, y2))
    def texture(sprite: TextureAtlasSprite): Texture =
      new Texture(sprite, List4(UV(x1 * 16F, y1 * 16F), UV(x2 * 16F, y1 * 16F), UV(x2 * 16F, y2 * 16F), UV(x1 * 16F, y2 * 16F)))
    def toQuad(face: Direction): Quad = Quad(
      corners.map { d =>
        (face match {
          case Direction.UP => Vertex(d.u, 1, 1 - d.v)
          case Direction.DOWN => Vertex(d.u, 0, d.v)
          case Direction.NORTH => Vertex(1 - d.u, d.v, 0)
          case Direction.SOUTH => Vertex(d.u, d.v, 1)
          case Direction.EAST => Vertex(1, d.v, 1 - d.u)
          case Direction.WEST => Vertex(0, d.v, d.u)
        }).transform(x => (x - 0.5F) * (1 + offs) + 0.5F)
      }, face)
  }

  val faceEdges: Map[Corner.Value, Rect] = Map(
    Corner.T -> Rect(0, 1 - edgeWidth, 1, 1),
    Corner.TR -> Rect(1 - edgeWidth, 1 - edgeWidth, 1, 1),
    Corner.R -> Rect(1 - edgeWidth, 0, 1, 1),
    Corner.BR -> Rect(1 - edgeWidth, 0, 1, edgeWidth),
    Corner.B -> Rect(0, 0, 1, edgeWidth),
    Corner.BL -> Rect(0, 0, edgeWidth, edgeWidth),
    Corner.L -> Rect(0, 0, edgeWidth, 1),
    Corner.TL -> Rect(0, 1 - edgeWidth, edgeWidth, 1),
    Corner.ALL -> Rect(0, 0, 1, 1)
  )

  val faceQuads: Map[(Corner.Value, Direction), Quad] = {
    for ((edge, rect) <- faceEdges; face <- Direction.values().toList)
      yield (edge, face) -> rect.toQuad(face)
  }
}
