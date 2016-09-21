#! /usr/bin/env python

from socket import *
import sys
import time

sock = socket(AF_INET, SOCK_STREAM);
HOST = '127.0.0.1'
PORT = 10000

sock.connect( (HOST, PORT) )

while(1):
	incomming = sock.recv(4096)	
	print incomming[:-1]

	outgoing = raw_input()
	if(outgoing == 'quit'):
		sock.close()
		sys.exit(0)
	sock.sendall(outgoing + '\n')
	time.sleep(0.16) # Give the server time to respond
