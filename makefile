all: build run

build:
	@echo "Building..."
	./build.sh

run:
	./run.sh

clean:
	rm -r "out/production/Tic-Tac-Toe"