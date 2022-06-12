CC = gcc
CFLAGS = -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS = src/*.h Makefile

all: bin/Sort_main bin/GenericDynamicArray_tests bin/SortingLib_tests

testsSortinglib: bin/SortingLib_tests
	bin/SortingLib_tests

testsDynamicArraylib: bin/GenericDynamicArray_tests
	bin/GenericDynamicArray_tests

create_folder:
	mkdir -p build
	mkdir -p bin

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@
	
lib/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/Sort_main: build/SortingLib.o build/GenericDynamicArray_lib.o build/Sort_main.o $(COMMON_DEPS)
	$(CC) -o bin/Sort_main build/SortingLib.o build/GenericDynamicArray_lib.o build/Sort_main.o

bin/SortingLib_tests: build/SortingLib_tests.o build/SortingLib.o build/GenericDynamicArray_lib.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/SortingLib_tests build/SortingLib_tests.o build/SortingLib.o build/GenericDynamicArray_lib.o lib/unity.o

bin/GenericDynamicArray_tests: build/GenericDynamicArray_lib.o lib/unity.o build/GenericDynamicArray_tests.o $(COMMON_DEPS)
	$(CC) -o bin/GenericDynamicArray_tests build/GenericDynamicArray_lib.o build/GenericDynamicArray_tests.o lib/unity.o

clean:
	$(RM) build/*
	$(RM) bin/*

run: bin/Sort_main
	time ./bin/Sort_main "/home/federico/Documents/es1_dataset/records.csv" "/home/federico/Documents/es1_dataset/out.csv" "2" "std_quicksort"
