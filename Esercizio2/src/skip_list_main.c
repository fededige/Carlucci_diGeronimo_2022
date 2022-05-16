#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h> 
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

static void load_SkipList(const char *dictionary, SkipList *list){
    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    fp = open_file(dictionary, "r");
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
            //printf("E' vuoto\n");
        }
        node = node->next[0];
        //printf("element->word: %s\n", element->word);
        free(element->word);
        free(element);
    }
    free_memory(list);
}
/*
[]---------> NIL
[]->[]->[]-> NIL

*/

void test_with_comparison_function(const char *dictionary, const char *file_name,int (*compare)(void *, void *)){
    FILE *fp;
    fp = open_file(file_name, "r");
    char ch = '\n';
    int flag=0;
    char *str;

    SkipList *list = CreateSkipList(compare);
    load_SkipList(dictionary, list);

    
    while(ch != -1){
        ch = (char) fgetc(fp);
        str = (char *) calloc(2, sizeof(char) * 2);
        flag = 0;
        while((ch >= 'A' && ch <='Z') || (ch >= 'a' && ch <= 'z')){
            if((ch >= 'A' && ch <='Z')){
                //ch = (char) ch + 32; //non funziona a causa di -Wconversion
                ch = (char) tolower(ch);
            }
            flag = 1;
            str = strncat(str, &ch, 1);
            str = realloc(str, strlen(str) + sizeof(char) +1);
            ch = (char) fgetc(fp);
        }
        if(flag==1){
            Record *serched_el = malloc(sizeof(Record));
            if (serched_el == NULL){
                fprintf(stderr, "main: unable to allocate memory for the read record");
                exit(EXIT_FAILURE);
            }
            serched_el->word = (char *)malloc((strlen(str) * sizeof(char)) + 1);

            if (serched_el->word == NULL){
                fprintf(stderr, "main: unable to allocate memory for the string of the read record");
                exit(EXIT_FAILURE);
            }

            strcpy(serched_el->word, str);
            
            if(searchSkipList(list, serched_el) == NULL){
                printf("%s\n",serched_el->word);
            }
            free(serched_el->word);
            free(serched_el);
        }
        free(str);
    }
    fclose(fp);
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
    //printf("primo el: %s, secondo el: %s\n", rec1_p->word, rec2_p->word);
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
    if (argc < 3){
        Usage();
        exit(EXIT_FAILURE);
    }
    test_with_comparison_function(argv[1], argv[2], compare);
}