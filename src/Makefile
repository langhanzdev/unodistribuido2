all:		 UnoClient.class UnoServer.class UnoImpl.class UnoInterface.class

UnoImpl.class:	UnoImpl.java UnoInterface.class
			@javac UnoImpl.java

UnoInterface.class:	UnoInterface.java
			@javac UnoInterface.java

UnoClient.class:	UnoClient.java
			@javac UnoClient.java
			
UnoServer.class:	UnoServer.java
			@javac UnoServer.java

run:			all
			@java UnoServer &
			@sleep 1
			@java UnoClient

clean:
			@rm -f *.class *~

info:
			@echo "(c) Roland Teodorowitsch (08 abr. 2015)"

