CC = gcc
CFLAGS = -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS = src/*.h Makefile

all: bin/bi_insertion_sort_main

create_folder:
	mkdir -p build
	mkdir -p bin

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@


bin/bi_insertion_sort_main: build/bi_insertion_sort.o build/bi_insertion_sort_main.o $(COMMON_DEPS)
	$(CC) -o bin/bi_insertion_sort_main build/bi_insertion_sort.o build/bi_insertion_sort_main.o

clean:
	$(RM) build/*
	$(RM) bin/*

run: bin/bi_insertion_sort_main
	./bin/bi_insertion_sort_main /home/emanuele/Documenti/recordmini_2.csv /home/emanuele/Documenti/out.csv
