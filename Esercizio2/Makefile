CC = gcc
CFLAGS = -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS = src/*.h Makefile

all: bin/skip_list_main

create_folder:
	mkdir -p build
	mkdir -p bin

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/skip_list_main: build/skip_list_lib.o build/skip_list_main.o $(COMMON_DEPS)
	$(CC) -o bin/skip_list_main build/skip_list_lib.o build/skip_list_main.o

clean:
	$(RM) build/*
	$(RM) bin/*

run: bin/skip_list_main
	./bin/skip_list_main "/home/emanuele/Documenti/dictionary.txt" "/home/emanuele/Documenti/correctme.txt" 