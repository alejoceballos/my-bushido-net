#!/bin/sh

set -e

# Calls the script to create the queues
/opt/mqm/bin/runmqsc MBDN_QM < /temp/mq.config

# Display MQ version details
# /opt/mqm/bin/dspmqver

# Command to start a messaging session to the queue on the manager
# /opt/mqm/samp/bin/amqsput MBDN.TO.CONTACTS.SAVE MBDN_QM
