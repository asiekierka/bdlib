/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.sensors.widgets

import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.Widget
import net.bdew.lib.sensors.SensorPair

import scala.collection.mutable

class WidgetSensorParam[T](val p: Point, config: => SensorPair[T, _], obj: => Option[T]) extends Widget {
  override val rect = new Rect(p, 16, 16)

  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) =
    for (x <- obj; s <- config.sensor.getParamTooltip(x, config.param)) tip += s

  override def draw(mouse: Point) =
    obj foreach (x => config.sensor.drawParameter(rect, parent, x, config.param))
}
