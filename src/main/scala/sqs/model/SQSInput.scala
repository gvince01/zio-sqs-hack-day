package sqs.model

case class SQSInput(
  requestPayload: Map[String, String],
  responsePayload: ResponsePayload
)
