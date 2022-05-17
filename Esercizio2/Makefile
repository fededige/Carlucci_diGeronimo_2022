CC = gcc
CFLAGS = -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS = src/*.h Makefile

all: bin/skip_list_main bin/skip_list_tests

tests: bin/skip_list_tests
	bin/skip_list_tests

create_folder:
	mkdir -p build
	mkdir -p bin

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

lib/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/skip_list_main: build/skip_list_lib.o build/skip_list_main.o $(COMMON_DEPS)
	$(CC) -o bin/skip_list_main build/skip_list_lib.o build/skip_list_main.o

bin/skip_list_tests: build/skip_list_tests.o build/skip_list_lib.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/skip_list_tests build/skip_list_tests.o build/skip_list_lib.o lib/unity.o

clean:
	$(RM) build/*
	$(RM) bin/*

run: bin/skip_list_main
	./bin/skip_list_main "/home/federico/Documents/es2_dataset/dictionary.txt" "/home/federico/Documents/es2_dataset/correctme.txt" 