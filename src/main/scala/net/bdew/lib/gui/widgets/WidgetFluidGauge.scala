package net.bdew.lib.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import net.bdew.lib.Text
import net.bdew.lib.data.DataSlotTankBase
import net.bdew.lib.gui.{Color, Point, Rect, Texture}
import net.minecraft.network.chat.Component

import scala.collection.mutable.ArrayBuffer

class WidgetFluidGauge(val rect: Rect, overlay: Texture, dSlot: DataSlotTankBase) extends Widget {
  override def handleTooltip(p: Point, tip: ArrayBuffer[Component]): Unit = {
    if (!dSlot.getFluid.isEmpty) {
      tip += dSlot.getFluid.getFluid.getAttributes.getDisplayName(dSlot.getFluid)
      tip += Text.fluidCap(dSlot.getFluidAmount, dSlot.getCapacity)
    } else {
      tip += Text.translate("bdlib.label.empty")
    }
  }

  override def draw(m: PoseStack, mouse: Point, partial: Float): Unit = {
    val fStack = dSlot.getFluid
    if (!fStack.isEmpty) {
      val color = Color.fromInt(fStack.getFluid.getAttributes.getColor(fStack))
      val icon = Texture.block(fStack.getFluid.getAttributes.getStillTexture(fStack))
      val fillHeight = if (dSlot.getCapacity > 0) rect.h * fStack.getAmount / dSlot.getCapacity else 0
      parent.drawTextureTiled(m, Rect(rect.x, rect.y + rect.h - fillHeight, rect.w, fillHeight), icon, 16, 16, color)
    }
    if (overlay != null)
      parent.drawTexture(m, rect, overlay)
  }
}
