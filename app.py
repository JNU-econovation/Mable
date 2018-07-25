# -*-coding:UTF-8 -*-

import logging
import time
import datetime
import RPi. GPIO as GPIO
from flask import Flask, request
from flask_ask import Ask, statement
from flaskext.mysql import MySQL
import time
import datetime
import RPi.GPIO as GPIO

led1 = 14
led2 = 15
led3 = 23
sensor1 = 17
sensor2 = 18
sensor3 = 24
mysql = MySQL()
app = Flask(__name__)

app.config['MYSQL_DATABASE_USER']='root'
app.config['MYSQL_DATABASE_PASSWORD'] = '1234'
app.config['MYSQL_DATABASE_DB'] = 'mysql'
app.config['MYSQL_DATABASE_HOST']='localhost'

mysql.init_app(app)

ask = Ask(app, '/')
logging.getLogger("flask_ask").setLevel(logging.DEBUG)

@app.route('/')
def hello_world():
	return 'Hello World'

@ask.launch
def start_skill():
	welcome_message = "Hello Mable, I'm ready"
	return statement(welcome_message)


@ask.intent('medicinecheck')
def medicinecheck():
	connect = mysql.connect()
	cursor = connect.cursor()
	cursor.execute("select check_medicine from mable")
	data = cursor.fetchone()

	if data:
		text = "You've been take your medicine."
	else:
		text = "You've not been take your medicine."

	connect.commit()
	connect.close()
	return statement(text)


@app.route('/android')
def android_return():
	connect = mysql.connect()
	cursor = connect.cursor()

	print("return in")
	cursor.execute("select check_medicine from mable")
	data = cursor.fetchone()
	if data is 1:
		text = "true"
	else:
		text = "false"
	connect.commit()
	connect.close()
	return text


@app.route('/set_medicine_open', methods=['GET'])
def update_open():
	data = request.args.get("token_")
	print(data)

	connect = mysql.connect()
	cursor = connect.cursor()
	if data == "true":
		print("true in")
		cursor.execute("update mable set check_medicine='1'")
		connect.commit()
		connect.close()
		return "set true"
	else:
		print("false in")
		cursor.execute("update mysql.mable set check_medicine='0'")
		connect.commit()
		connect.close()
		return "set false"
	connect.commit()
	connect.close()


def sensorCallback(channel):
	timestamp = time.time()
	stamp = datetime.datetime.fromtimestamp(timestamp).strftime('%H:%M:%S')
	if GPIO.input(channel):
		print(stamp + " : #sensor" + str(1 if channel == sensor1 else 2 if channel == sensor2 else 3) + " opened")
		if channel == sensor1:
			GPIO.output(led1, GPIO.HIGH)
			android_return();
		elif channel == sensor2:
			GPIO.output(led2, GPIO.HIGH)
			android_return();
		elif channel == sensor3:
			GPIO.output(led3, GPIO.HIGH)
			android_return();
	else:
		print(stamp + " : #sensor" + str(1 if channel == sensor1 else 2 if channel == sensor2 else 3) + " closed")
		if channel == sensor1:
			GPIO.output(led1, GPIO.LOW)
		elif channel == sensor2:
			GPIO.output(led2, GPIO.LOW)
		elif channel == sensor3:
			GPIO.output(led3, GPIO.LOW)

if __name__ == '__main__':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(sensor1, GPIO.IN)
	GPIO.setup(sensor2, GPIO.IN)
	GPIO.setup(sensor3, GPIO.IN)
	GPIO.setup(led1, GPIO.OUT)
	GPIO.setup(led2, GPIO.OUT)
	GPIO.setup(led3, GPIO.OUT)
	GPIO.add_event_detect(sensor1, GPIO.BOTH, callback=sensorCallback)
	GPIO.add_event_detect(sensor2, GPIO.BOTH, callback=sensorCallback)
	GPIO.add_event_detect(sensor3, GPIO.BOTH, callback=sensorCallback)
	app.run(debug=True)

	try:
		while True:
			time.sleep(0.1)
	except KeyboardInterrupt:
		GPIO.cleanup()




