/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.sensors.multiblock

import net.bdew.lib.multiblock.tile.TileController
import net.bdew.lib.sensors.{GenericSensorType, SensorSystem}
import net.minecraft.tileentity.TileEntity

trait CIRedstoneSensors extends TileController {
  def redstoneSensorsType: Seq[GenericSensorType[TileEntity, Boolean]]
  def redstoneSensorSystem: SensorSystem[TileEntity, Boolean]
}
