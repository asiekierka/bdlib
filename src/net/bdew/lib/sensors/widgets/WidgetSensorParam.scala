/*
 * Copyright (c) bdew, 2013 - 2015
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.sensors.widgets

import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.Widget
import net.bdew.lib.sensors.GenericSensorParameter

import scala.collection.mutable

class WidgetSensorParam(val p: Point, param: => GenericSensorParameter) extends Widget {
  override val rect = new Rect(p, 16, 16)
  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) = tip += param.localizedName
  override def draw(mouse: Point) = parent.drawTexture(rect, param.texture, param.textureColor)
}