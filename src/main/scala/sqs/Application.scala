package sqs

import io.circe.generic.auto._
import io.circe.parser._
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.Message
import sqs.model.SQSInput
import zio.sqs.{ SqsStream, SqsStreamSettings, Utils }
import zio.{ App, ExitCode, Task, UIO, URIO, ZEnv }

object Application extends App {


  private def createClient(): Task[SqsAsyncClient] = {
    Task {
      SqsAsyncClient
        .builder()
        .region(Region.of("eu-west-1"))
        .build()
    }
  }

  override def run(args: List[String]): URIO[ZEnv, ExitCode] =
    (for {
      client: SqsAsyncClient <- createClient()
      queueName = "George-HackDay-Queue"
      _ <- Utils.createQueue(client, queueName)
      queueUrl <- Utils.getQueueUrl(client, queueName)
      _ <- SqsStream(
        client,
        queueUrl,
        SqsStreamSettings(stopWhenQueueEmpty = false, waitTimeSeconds = Some(3))
      ).foreach(handleSQSMessage)
    } yield 0).foldM(e => UIO(println(e.toString)).as(ExitCode.failure), m => UIO(println(m)).as(ExitCode.success))


  private def handleSQSMessage: Message => UIO[String] = (msg: Message) => {
    val sqsInput = decode[SQSInput](msg.body()) match {
      case Left(error) => error.getMessage

      case Right(value) =>
        value.responsePayload.body.reverse
    }

    UIO(sqsInput)
  }
}
