/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.gui

import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

trait DrawTarget {
  def getZLevel: Float
  def getFontRenderer: FontRenderer
  def drawTexture(r: Rect, t: Texture, color: Color = Color.white)
  def drawText(text: String, p: Point, color: Color, shadow: Boolean)
  def drawTextMultiline(text: String, r: Rect, color: Color)

  def drawTextureInterpolate(r: Rect, t: Texture, x1: Float, y1: Float, x2: Float, y2: Float, color: Color = Color.white) =
    drawTexture(r.interpolate(x1, y1, x2, y2), Texture.interpolate(t, x1, y1, x2, y2), color)
}

trait SimpleDrawTarget extends DrawTarget {
  private final val T = Tessellator.getInstance()

  def drawTexture(r: Rect, t: Texture, color: Color = Color.white) {
    val z = getZLevel
    t.bind()
    color.activate()
    val W = T.getWorldRenderer
    W.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    W.pos(r.x1, r.y2, z).tex(t.u1, t.v2).endVertex()
    W.pos(r.x2, r.y2, z).tex(t.u2, t.v2).endVertex()
    W.pos(r.x2, r.y1, z).tex(t.u2, t.v1).endVertex()
    W.pos(r.x1, r.y1, z).tex(t.u1, t.v1).endVertex()
    T.draw()
  }

  def drawText(text: String, p: Point, color: Color, shadow: Boolean) =
    getFontRenderer.drawString(text, p.x.round, p.y.round, color.asARGB, shadow)

  def drawTextMultiline(text: String, r: Rect, color: Color) =
    getFontRenderer.drawSplitString(text, r.x.round, r.y.round, r.w.round, color.asARGB)
}
