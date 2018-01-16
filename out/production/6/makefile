all: compile jar

compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin @sources.txt
	rm sources.txt

bin:
	mkdir bin

jar:
	jar -cfm reversi.jar manifest.txt -C bin . -C res .
	chmod +x reversi.jar
run:
	java -jar reversi.jar

clean:
	rm reversi.jar
	rm -r bin
