//package com.ojha.persistence
//
//import akka.actor.{ActorSystem, Props}
//import akka.persistence._
//
//case class WriteCommand(data: String)
//case object PrintCommand
//case class DataWriteEvent(data: String)
//
//object Example extends App {
//  val system = ActorSystem()
//  val actor = system.actorOf(Props(new ExamplePersistentActor))
//  actor ! WriteCommand("Ruby")
//  actor ! PrintCommand
//}
//
//case class ExampleState(events: List[String] = Nil) {
//  def updated(evt: DataWriteEvent): ExampleState = copy(evt.data :: events)
//  def size: Int = events.length
//  override def toString: String = events.reverse.toString
//}
//
//class ExamplePersistentActor extends PersistentActor {
//  override def persistenceId = "example-id"
//
//  var state = ExampleState()
//
//  def updateState(event: DataWriteEvent): Unit = state = state.updated(event)
//
//  def numberOfEvents: Int = state.size
//
//  val receiveCommand: Receive = {
//    case WriteCommand(data) ⇒
//      persist(DataWriteEvent(s"$data-$numberOfEvents")) { event ⇒
//        updateState(event)
//        context.system.eventStream.publish(event)
//      }
//    case PrintCommand ⇒ println(state)
//  }
//
//  val receiveRecover: Receive = {
//    case evt: DataWriteEvent ⇒ updateState(evt)
//    case SnapshotOffer(_, snapshot: ExampleState) ⇒ state = snapshot
//  }
//
//}
//
