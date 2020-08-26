import json
import os

def lambda_handler(event, context):

    payload = event['payload']

    return {
        'statusCode': 200,
        'body': payload
    }
