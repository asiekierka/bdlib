package net.bdew.lib.multiblock.data

import net.bdew.lib.misc.RSMode
import net.bdew.lib.multiblock.network.{MsgOutputCfg, MsgOutputCfgRSMode}
import net.minecraft.nbt.CompoundTag

class OutputConfigItems extends OutputConfig with OutputConfigRSControllable {
  override def id = "items"
  var rsMode: RSMode.Value = RSMode.ALWAYS

  def read(t: CompoundTag): Unit = {
    rsMode = RSMode(t.getInt("rsMode"))
  }

  def write(t: CompoundTag): Unit = {
    t.putInt("rsMode", rsMode.id)
  }

  def handleConfigPacket(m: MsgOutputCfg): Unit = m match {
    case MsgOutputCfgRSMode(_, r) => rsMode = r
    case _ => sys.error("Invalid output config packet %s to config %s".format(m, this))
  }

}
