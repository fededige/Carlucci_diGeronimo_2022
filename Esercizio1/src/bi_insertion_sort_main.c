#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "bi_insertion_sort.h"

typedef struct _record {
    int id;
    char *field1;
    int field2;
    double field3;
} Record;

FILE *open_file(const char *filename){
  FILE *file = fopen(filename, "r");
  if (file == NULL) {
    printf("File not found: %s\n", filename);
    exit(1);
  }
  return file;
}

static  void print_array(Array *array) {
  unsigned long size = array_size(array);

  Record *array_element;
  printf("\nARRAY OF RECORDS\n");

  for (unsigned long i = 0; i < size; ++i) {
    array_element = (Record*)array_get(array, i);
    printf("%d, %s, %i, %f\n", array_element->id, array_element->field1, array_element->field2, array_element->field3);
  }
}

static  void free_array(Array *array) {
  unsigned long size = array_size(array);
  for (unsigned long i = 0; i < size; ++i) {
    Record *array_element = (Record*)array_get(array, i);
    free(array_element->field1);
    free(array_element);
  }
  ordered_array_free_memory(array);
}

static void load_array(const char *file_name, Array *array) {
  // char buffer[BUFFER_SIZE];
  FILE *fp;
  char *line = NULL;
  size_t len = 0;

  fp = open_file(file_name);


  while (getline(&line, &len, fp) != -1) {
    Record *record_p = malloc(sizeof(Record));
    if (record_p == NULL) {
      fprintf(stderr,"main: unable to allocate memory for the read record");
      exit(EXIT_FAILURE);
    }
    
    char *id_in_read_line_p = strtok(line, ",");  //andarsi a vedere come funziona la strtok
    char *field1_in_read_line_p = strtok(NULL, ",");
    char *field2_in_read_line_p = strtok(NULL, ",");
    char *field3_in_read_line_p = strtok(NULL, ",");

    //printf("%s, %s, %s, %s", id_in_read_line_p, field1_in_read_line_p, field2_in_read_line_p, field3_in_read_line_p);
    
    record_p->id = atoi(id_in_read_line_p);     //carico nel record l'ID
    record_p->field2 = atoi(field2_in_read_line_p);     //carico nel record il secondo campo
    record_p->field3 = atof(field3_in_read_line_p);     //carico nel record il terzo campo DA CONTROLLARE

  
    record_p->field1 = malloc((strlen(field1_in_read_line_p)+1) * sizeof(char));    //carico il primo campo nel record
    if (record_p->field1 == NULL) {
      fprintf(stderr,"main: unable to allocate memory for the string field of the read record");
      exit(EXIT_FAILURE);
    }
    strcpy(record_p->field1, field1_in_read_line_p);

    //printf("%d, %d, %f, %s\n", record_p->id, record_p->field2, record_p->field3, record_p->field1);

    array_insert(array, (void*)record_p);      //DA SCRIVERE, VEDERE DAL PROF DRAGO da qui in poi 
  }
  if (line) free(line);
  fclose(fp);
  printf("\nData loaded\n");
}

static void test_with_comparison_function(const char *file_name, int (*compare)(void*, void*)) {
  Array *array = binary_insertion_create(compare);
  load_array(file_name, array);
  //bi_insertion_sort_onarray(array, compare); //da implementare
  print_array(array);
  free_array(array);
}

static int compare_elements(void *r1_p, void *r2_p) {
  if (r1_p == NULL) {
    fprintf(stderr, "precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if (r2_p == NULL) {
    fprintf(stderr, "precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  return rand()%1; //non ha senso, solo momentaneo
}

int main(int argc, char const *argv[]) {
  if (argc < 2) {
    printf("ELEMENTI INSUFF");
    exit(EXIT_FAILURE);
  }
  test_with_comparison_function(argv[1], compare_elements);

  return EXIT_SUCCESS;
}