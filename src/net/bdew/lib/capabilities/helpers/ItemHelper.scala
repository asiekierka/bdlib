/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.capabilities.helpers

import net.bdew.lib.capabilities.CapAdapters
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler

object ItemHelper {

  import net.bdew.lib.capabilities.Capabilities.CAP_ITEM_HANDLER

  def hasItemHandler(world: World, pos: BlockPos, side: EnumFacing): Boolean = {
    val tile = world.getTileEntity(pos)
    if (tile == null)
      false
    else if (tile.hasCapability(CAP_ITEM_HANDLER, side))
      true
    else CapAdapters.get(CAP_ITEM_HANDLER).canWrap(tile, side)
  }

  def hasItemHandler(stack: ItemStack): Boolean = {
    if (stack == null || stack.getItem == null)
      false
    else if (stack.hasCapability(CAP_ITEM_HANDLER, null))
      true
    else CapAdapters.get(CAP_ITEM_HANDLER).canWrap(stack)
  }

  def getItemHandler(world: World, pos: BlockPos, side: EnumFacing): Option[IItemHandler] = {
    val tile = world.getTileEntity(pos)
    if (tile == null) return None
    if (tile.hasCapability(CAP_ITEM_HANDLER, side)) {
      val cap = tile.getCapability(CAP_ITEM_HANDLER, side)
      if (cap != null) return Some(cap)
    }
    CapAdapters.get(CAP_ITEM_HANDLER).wrap(tile, side)
  }

  def getItemHandler(stack: ItemStack): Option[IItemHandler] = {
    if (stack == null || stack.getItem == null) return None
    if (stack.hasCapability(CAP_ITEM_HANDLER, null)) {
      val cap = stack.getCapability(CAP_ITEM_HANDLER, null)
      if (cap != null) return Some(cap)
    }
    CapAdapters.get(CAP_ITEM_HANDLER).wrap(stack)
  }
}
