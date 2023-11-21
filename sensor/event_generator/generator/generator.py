#!/usr/bin/env python
import pika, json

from sample_events import traffic, temperature_levels, light_levels, aq_levels

QUEUE_NAME = 'snap_park'
HOST = 'localhost'

def send_event(event, queue_name, conn):
    channel = conn.channel()
    channel.queue_declare(queue='snap_park')
    channel.basic_publish(exchange='', routing_key=queue_name, body=event)

def main():
    connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=HOST))

    connection.close()

if __name__ == "__main__":
    main()