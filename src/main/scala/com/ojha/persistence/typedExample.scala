package com.ojha.persistence

import akka.actor.typed.Behavior
import akka.persistence.typed.scaladsl.PersistentActor
import akka.persistence.typed.scaladsl.PersistentActor.Effect

sealed trait TypedExampleCommand extends Serializable
case class TypedExampleWriteCommand(data: String) extends TypedExampleCommand
case object TypedExamplePrint extends TypedExampleCommand

case class TypedEvent(data: String)

case class TypedExampleState(events: List[String] = Nil) {
  def updated(evt: TypedEvent): TypedExampleState = copy(evt.data :: events)
  def size: Int = events.length
  override def toString: String = events.reverse.toString
}

object TypedExampleMain extends App {

  import akka.actor.typed.scaladsl.adapter._

  val system = akka.actor.ActorSystem("system")
  val actor = system.spawn(TypedExample.behavior, "example")
  actor ! TypedExampleWriteCommand("Hi Bella")
  actor ! TypedExamplePrint
}

object TypedExample {

  def behavior: Behavior[TypedExampleCommand] =
    PersistentActor.immutable[TypedExampleCommand, TypedEvent, TypedExampleState](
      persistenceId = "example-id",
      initialState = new TypedExampleState,
      commandHandler = PersistentActor.CommandHandler {
        (_, state, cmd) ⇒
          cmd match {
            case TypedExampleWriteCommand(data) => Effect.persist(TypedEvent(s"$data ${state.size}"))
            case TypedExamplePrint => println(state); Effect.none
          }
      },
      eventHandler = (state, event) ⇒ event match {
        case TypedEvent(_) => state.updated(event)
      }
    )

}

