#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "bi_insertion_sort.h"

#define PROGRAM "/bin/bi_insertion_sort_main"

typedef struct _record { 
    int id;
    char *field1;
    int field2;
    double field3;
} Record;

void Usage(){
  fprintf(stderr, "\nUSAGE\t: \t%s ", PROGRAM);
  fprintf(stderr, "\"insert input csv pathname\" ");
  fprintf(stderr, "\"insert output csv pathname\" ");
  fprintf(stderr, "insert field to sort [1 | 2 | 3] ");
  fprintf(stderr, "insert algo name [bi_insertionsort | quicksort]\n");
  
  //fprintf(stderr, " text  File to use as testing.\n");
  exit(1);
}

FILE *open_file(const char *filename, char *m){
  FILE *file = fopen(filename, m);
  if (file == NULL) {
    printf("File not found: %s\n", filename);
    exit(1);
  }
  return file;
}

static  void print_array(Array *array, FILE *out) {
  unsigned long size = array_size(array);

  Record *array_element;
  
  for (unsigned long i = 0; i < size; ++i) {
    array_element = (Record*)array_get(array, i);
    fprintf(out, "%d, %s, %i, %f\n", array_element->id, array_element->field1, array_element->field2, array_element->field3);
  }
  printf("\nData written\n");
  fclose(out);
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
  FILE *fp;
  char *line = NULL;
  size_t len = 0;

  fp = open_file(file_name, "r");


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

    
    
    record_p->id = atoi(id_in_read_line_p);     //carico nel record l'ID
    record_p->field2 = atoi(field2_in_read_line_p);     //carico nel record il secondo campo
    record_p->field3 = atof(field3_in_read_line_p);     //carico nel record il terzo campo DA CONTROLLARE

  
    record_p->field1 = malloc((strlen(field1_in_read_line_p)+1) * sizeof(char));    //carico il primo campo nel record
    if (record_p->field1 == NULL) {
      fprintf(stderr,"main: unable to allocate memory for the string field of the read record");
      exit(EXIT_FAILURE);
    }
    strcpy(record_p->field1, field1_in_read_line_p);

    

    array_insert(array, (void*)record_p);      //DA SCRIVERE, VEDERE DAL PROF DRAGO da qui in poi 
  }
  if (line) free(line);
  fclose(fp);
  printf("\nData loaded\n");
}

static void test_with_comparison_function(const char *file_name, const char *file_name_out, const char *mode, int (*compare)(void*, void*)) {
  FILE *out = open_file(file_name_out, "w"); 
  /*If a file with the same name already exists its contents 
  are erased and the file is treated as an empty new file.*/
  Array *array = binary_insertion_create(compare);
  load_array(file_name, array);

  if(strcmp(mode, "quicksort") == 0){
    array = rand_quicksort(array);
  }
  else if(strcmp(mode, "bi_insertionsort") == 0){
    array = bi_insertion_sort(array);
  }else{
    Usage();
    exit(EXIT_FAILURE);
  } 
  
  printf("array sorted");
  print_array(array, out);
  free_array(array);
}

static int compare_field3(void *r1_p, void *r2_p) {
  if (r1_p == NULL) {
    fprintf(stderr, "precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if (r2_p == NULL) {
    fprintf(stderr, "precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  Record *rec1_p = (Record*)r1_p;
  Record *rec2_p = (Record*)r2_p;
  if(rec1_p->field3 < rec2_p->field3){
    return -1;
  }
  else if(rec1_p->field3 > rec2_p->field3){
    return 1;
  }
  else return 0;
}

static int compare_field2(void *r1_p, void *r2_p) {
  if (r1_p == NULL) {
    fprintf(stderr, "precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if (r2_p == NULL) {
    fprintf(stderr, "precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  Record *rec1_p = (Record*)r1_p;
  Record *rec2_p = (Record*)r2_p;
  if(rec1_p->field2 < rec2_p->field2){
    return -1;
  }
  else if(rec1_p->field2 > rec2_p->field2){
    return 1;
  }
  else return 0;
}

static int compare_field1(void *r1_p, void *r2_p) {
  int res;
  if (r1_p == NULL) {
    fprintf(stderr, "precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if (r2_p == NULL) {
    fprintf(stderr, "precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  Record *rec1_p = (Record*)r1_p;
  Record *rec2_p = (Record*)r2_p;
  res = strcmp(rec1_p->field1, rec2_p->field1);
  if(res < 0){
    return -1;
  }
  else if(res > 0){
    return 1;
  }
  else return 0;
}

int main(int argc, char const *argv[]) {
  if (argc < 5) {
    Usage();
    exit(EXIT_FAILURE);
  }
  switch(atoi(argv[3])){
    case 1:
      test_with_comparison_function(argv[1], argv[2], argv[4], compare_field1);
      break;
    case 2:
      test_with_comparison_function(argv[1], argv[2], argv[4], compare_field2);
      break;
    case 3:
      test_with_comparison_function(argv[1], argv[2], argv[4], compare_field3);
      break;
    default:
      Usage();
      exit(EXIT_FAILURE);
  }

  return EXIT_SUCCESS;
}