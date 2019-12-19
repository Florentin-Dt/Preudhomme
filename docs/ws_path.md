# WEB SERVICE PATH LIST

All path is preceded by context, context corresponding to :
- 1 : device path
- 2 : path you give in tomcat-deployment 
- 3 : web service general path /api/

My local example : localhost:8080/tma/api

## Command

ws src path :

	/order

simulate function path :
	/order/simulate?idMagasin=1

Path parameter take idMagasin id to simulate it's new command

update function path : 

	/order/update?idCommande=1,idEtat=1	

Development in progress,
Parameter idCommande take id of command updated
Parameter idEtat take id of target state update

clear all order : 

	/order/clear

Development in progress

clear all 4 status command : 

	/order/cleartoday

Development in progres

## CommandLog

ws src path : 
	
	/orderlog

log reader function : 

	/orderlog/read

## Monitor

ws src path : 

	/monitor

Information reading : 

	/read

in progress, actually working but don't return target db version

update db version :

	/update


