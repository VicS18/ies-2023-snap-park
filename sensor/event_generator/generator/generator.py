#!/usr/bin/env python
import pika, json

from sample_events import TRAFFIC, TEMPERATURE_LEVELS, LIGHT_LEVELS, AQ_LEVELS

QUEUE_NAME = 'snap_park'
HOST = 'localhost'

def dummy(queue_name, conn):
    channel = conn.channel()
    channel.queue_declare(queue=queue_name)
    for t in TRAFFIC:
        channel.basic_publish(exchange='', routing_key=queue_name, body=json.dumps(t))
    for tl in TEMPERATURE_LEVELS:
        channel.basic_publish(exchange='', routing_key=queue_name, body=json.dumps(tl))
    for ll in LIGHT_LEVELS:
        channel.basic_publish(exchange='', routing_key=queue_name, body=json.dumps(ll))
    for aql in AQ_LEVELS:
        channel.basic_publish(exchange='', routing_key=queue_name, body=json.dumps(aql))

def main():
    connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=HOST))

    dummy(QUEUE_NAME, connection)

    connection.close()

if __name__ == "__main__":
    main()