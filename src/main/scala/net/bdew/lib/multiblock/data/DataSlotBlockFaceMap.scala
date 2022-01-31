package net.bdew.lib.multiblock.data

import net.bdew.lib.PimpVanilla._
import net.bdew.lib.block.BlockFace
import net.bdew.lib.data.base.{DataSlot, DataSlotContainer, UpdateKind}
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag

import scala.collection.mutable

case class DataSlotBlockFaceMap(name: String, parent: DataSlotContainer) extends DataSlot {
  val map = collection.mutable.Map.empty[BlockFace, Int]

  setUpdate(UpdateKind.SAVE, UpdateKind.WORLD)

  def inverted: Map[Int, BlockFace] = map.toMap.map(_.swap)

  def ent2arr(x: (BlockFace, Int)): Array[Int] = Array(x._2, x._1.x, x._1.y, x._1.z, x._1.face.ordinal())
  def arr2ent(x: Array[Int]): (BlockFace, Int) = BlockFace(x(1), x(2), x(3), Direction.values()(x(4))) -> x(0)

  def save(t: CompoundTag, kind: UpdateKind.Value): Unit = {
    t.setListVals(name, map.map(ent2arr))
  }

  def load(t: CompoundTag, kind: UpdateKind.Value): Unit = {
    map.clear()
    map ++= t.getListVals[Array[Int]](name) map arr2ent
  }

  def updated(): Unit = parent.dataSlotChanged(this)
}

object DataSlotBlockFaceMap {

  import scala.language.implicitConversions

  implicit def dataSlotBlockFaceMap2map(v: DataSlotBlockFaceMap): mutable.Map[BlockFace, Int] = v.map
}
