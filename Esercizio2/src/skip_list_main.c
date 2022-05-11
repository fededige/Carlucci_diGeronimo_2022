#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "skip_list_lib.h"

#define PROGRAM "/bin/skip_list_main"

typedef struct _record { 
    char *word;
} Record;

FILE *open_file(const char *filename, char *m){
  FILE *file = fopen(filename, m);
  if (file == NULL) {
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

    while(getline(&line, &len, fp) != -1){
      Record *record_p = malloc(sizeof(Record));
        if (record_p == NULL) {
          fprintf(stderr,"main: unable to allocate memory for the read record");
          exit(EXIT_FAILURE);
        }

    record_p->word = malloc((strlen(line) + 1) * sizeof(char));
  
    if (record_p->word == NULL) {
      fprintf(stderr,"main: unable to allocate memory for the string of the read record");
      exit(EXIT_FAILURE);
    }
    strcpy(record_p->word, line);
    insertSkipList(list, (void*)record_p);
  }
  if (line) free(line);
  fclose(fp);
  printf("\nData loaded\n");
}

void Usage(){
  fprintf(stderr, "\nUSAGE\t: \t%s ", PROGRAM);
  fprintf(stderr, "\"insert input csv pathname\" ");  
  exit(1);
}

void test_with_comparison_function(const char *file_name, int(*compare)(void*, void*)){
    Record *serched_el = (Record*) malloc(sizeof(Record));
    serched_el->word = (char*) malloc(5 * sizeof(char));
    strcpy(serched_el->word, "ciao");
    printf("Elemento %p\n ",serched_el->word);
    SkipList *list = CreateSkipList(compare);
    load_SkipList(file_name, list);
    /*if(searchSkipList(list, serched_el->word) == NULL){
        fprintf(stderr, "elemento insesistente\n");
    }
    else{
      printf("trovato");
    }*/
    free_SkipList(list);
    free(serched_el);
}

static int compare(void *r1_p, void *r2_p) {
  if(r1_p == NULL) {
    fprintf(stderr, "precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if (r2_p == NULL) {
    fprintf(stderr, "precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  Record *rec1_p = (Record*)r1_p;
  Record *rec2_p = (Record*)r2_p;
  return strcmp(rec1_p->word, rec2_p->word) < 0;
}

int main(int argc, char const *argv[]){
    if(argc < 2){
        Usage();
        exit(EXIT_FAILURE);
    }
    test_with_comparison_function(argv[1], compare);
}