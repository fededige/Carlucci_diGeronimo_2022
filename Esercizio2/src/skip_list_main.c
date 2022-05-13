#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "skip_list_lib.h"

#define PROGRAM "/bin/skip_list_main"

/*

verificare il comportamento tramite valgrind: serched_el per qualche motivo non viene liberato, anzi ne viene perso il riferimento

*/

typedef struct _record{
    char *word;
} Record;

FILE *open_file(const char *filename, char *m){
    FILE *file = fopen(filename, m);
    if (file == NULL){
        printf("File not found: %s\n", filename);
        exit(1);
    }
    return file;
}

static void load_SkipList(const char *file_name, SkipList *list){
    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    fp = open_file(file_name, "r");
    while (getline(&line, &len, fp) != -1)    {
        Record *record_p = malloc(sizeof(Record));

        if (record_p == NULL){
            fprintf(stderr, "main: unable to allocate memory for the read record");
            exit(EXIT_FAILURE);
        }

        if (line[strlen(line) - 1] == '\n'){ // facciamo in modo che ogni stringa non finisca con uno /n
            line[strlen(line) - 1] = '\0';
        }

        record_p->word = (char *)malloc((strlen(line) + 1) * sizeof(char));

        if (record_p->word == NULL){
            fprintf(stderr, "main: unable to allocate memory for the string of the read record");
            exit(EXIT_FAILURE);
        }

        strcpy(record_p->word, line);

        // printf("WORD: parola: %s, lunghezza: %li \n", record_p->word, strlen(record_p->word));

        insertSkipList(list, (void *)record_p);
        // free(record_p->word);
    } // end while
    if (line)
        free(line);
    fclose(fp);
    printf("\nData loaded\n");
}

void Usage(){
    fprintf(stderr, "\nUSAGE\t: \t%s ", PROGRAM);
    fprintf(stderr, "\"insert input csv pathname\" ");
    exit(1);
}

void free_SkipList(SkipList *list){
    if (list == NULL){
        return;
    }
    Node *node = list->head->next[0];
    if (node == NULL){
        free_memory(list);
        return;
    }
    while (node != NULL){
        Record *element = (Record *)list_get(node);
        if (element == NULL){
            printf("E' vuoto\n");
        }
        node = node->next[0];
        printf("element->word: %s\n", element->word);
        free(element->word);
        free(element);
    }
    free_memory(list);
}
/*
[]---------> NIL
[]->[]->[]-> NIL

*/

void test_with_comparison_function(const char *file_name, int (*compare)(void *, void *)){
    Record *serched_el = (Record *)malloc(sizeof(Record));
    serched_el->word = (char *)malloc(5 * sizeof(char));
    strcpy(serched_el->word, "riav");
    printf("Elemento %p\n ", serched_el->word);
    SkipList *list = CreateSkipList(compare);
    load_SkipList(file_name, list);
    if (searchSkipList(list, serched_el) == NULL){
        fprintf(stderr, "elemento inesistente\n");
    }
    else{
        printf("trovato\n");
    }
    free(serched_el);
    free_SkipList(list);
}

static int compare(void *r1_p, void *r2_p){
    if (r1_p == NULL){
        fprintf(stderr, "precedes_string: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL){
        fprintf(stderr, "precedes_string: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    Record *rec1_p = (Record *)r1_p;
    Record *rec2_p = (Record *)r2_p;

    int value = strcmp(rec1_p->word, rec2_p->word);
    if (value > 0){
        return 1;
    }
    else if (value < 0){
        return -1;
    }
    return value;
}

int main(int argc, char const *argv[]){
    if (argc < 2){
        Usage();
        exit(EXIT_FAILURE);
    }
    test_with_comparison_function(argv[1], compare);
}