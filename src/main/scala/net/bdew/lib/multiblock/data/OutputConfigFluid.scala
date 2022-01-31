package net.bdew.lib.multiblock.data

import net.bdew.lib.PimpVanilla._
import net.bdew.lib.misc.RSMode
import net.bdew.lib.multiblock.network.{MsgOutputCfg, MsgOutputCfgRSMode}
import net.minecraft.nbt.CompoundTag

import scala.collection.mutable

class OutputConfigFluid extends OutputConfig with OutputConfigRSControllable {
  override def id = "fluid"
  val values: mutable.Queue[Double] = mutable.Queue.empty[Double]
  var rsMode: RSMode.Value = RSMode.ALWAYS

  final val ticks = 20

  def avg: Double = if (values.nonEmpty) values.sum / values.size else 0

  def updateAvg(v: Double): Unit = {
    values += v
    if (values.size > ticks)
      values.dequeue()
  }

  def read(t: CompoundTag): Unit = {
    values.clear()
    values ++= t.getListVals[Double]("values")
    rsMode = RSMode(t.getInt("rsMode"))
  }

  def write(t: CompoundTag): Unit = {
    t.setListVals("values", values)
    t.putInt("rsMode", rsMode.id)
  }

  def handleConfigPacket(m: MsgOutputCfg): Unit = m match {
    case MsgOutputCfgRSMode(_, r) => rsMode = r
    case _ => sys.error("Invalid output config packet %s to config %s".format(m, this))
  }
}
