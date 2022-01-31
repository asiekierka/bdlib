package net.bdew.lib.data

import net.bdew.lib.data.base.{DataSlot, DataSlotContainer, UpdateKind}
import net.minecraft.nbt.CompoundTag

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, DataOutputStream}
import scala.collection.mutable

case class DataSlotMovingAverage(name: String, parent: DataSlotContainer, size: Int) extends DataSlot {
  val values: mutable.Queue[Double] = mutable.Queue.empty[Double]
  var average: Double = 0

  setUpdate(UpdateKind.GUI, UpdateKind.SAVE)

  def reset(): Unit = {
    values.clear()
    average = 0
  }

  def update(v: Double): Unit = {
    values += v
    if (values.length > size)
      values.dequeue()
    average = values.sum / values.length
    parent.dataSlotChanged(this)
  }

  override def save(t: CompoundTag, kind: UpdateKind.Value): Unit = {
    if (kind == UpdateKind.GUI) {
      t.putDouble(name, average)
    } else {
      val b = new ByteArrayOutputStream()
      val o = new DataOutputStream(b)
      o.writeByte(values.size)
      values.foreach(o.writeDouble)
      t.putByteArray(name, b.toByteArray)
      o.close()
    }
  }

  override def load(t: CompoundTag, kind: UpdateKind.Value): Unit = {
    if (kind == UpdateKind.GUI) {
      average = t.getDouble(name)
    } else {
      values.clear()
      if (t.contains(name)) {
        val i = new DataInputStream(new ByteArrayInputStream(t.getByteArray(name)))
        val len = i.readByte()
        values ++= (for (_ <- 0 until Math.min(len, size)) yield i.readDouble())
        i.close()
      }
    }
  }
}
