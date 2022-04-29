CC = gcc
CFLAGS = -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS = src/*.h Makefile

all: bin/sort_main bin/sorting_lib_tests

tests: bin/sorting_lib_tests
	bin/sorting_lib_tests

create_folder:
	mkdir -p build
	mkdir -p bin

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@
	
lib/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/sort_main: build/sorting_lib.o build/sort_main.o $(COMMON_DEPS)
	$(CC) -o bin/sort_main build/sorting_lib.o build/sort_main.o

bin/sorting_lib_tests: build/sorting_lib_tests.o build/sorting_lib.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/sorting_lib_tests build/sorting_lib_tests.o build/sorting_lib.o lib/unity.o

clean:
	$(RM) build/*
	$(RM) bin/*

run: bin/sort_main
	./bin/sort_main "/home/federico/Documents/recordmini_1.csv" "/home/federico/Documents/out.csv" "1" "std_quicksort"
