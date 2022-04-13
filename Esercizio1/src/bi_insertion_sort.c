#include "bi_insertion_sort.h"
#include <stdlib.h>
#include <stdio.h>

#define INITIAL_CAPACITY 2


static unsigned long bi_get_index(Array *array, unsigned long a, unsigned long b, void *key);
static void insert_element(Array *array, void *element, unsigned long index, unsigned long b);


struct _Array {
  void **array;
  unsigned long size;
  unsigned long array_capacity;
  int (*precedes)(void*, void*);
};

void *array_get(Array *array, unsigned long i) {
  if (array == NULL) {
    fprintf(stderr, "array_get: array parameter cannot be NULL");
    return NULL;
  }
  if (i >= array->size) {
    fprintf(stderr, "array_get: Index %lu is out of the array bounds", i);
    return NULL;
  }
  return array->array[i];
}

unsigned long array_size(Array *array) {
  if (array == NULL) {
    return 0;
  }
  return array->size;
}

Array *binary_insertion_create(int (*precedes) (void *a, void *b)){
    if (precedes == NULL) {
        fprintf(stderr, "binary_insertion_create: precedes parameter cannot be NULL");
        return NULL;
    }

    Array *array = (Array*)malloc(sizeof(Array));

    if (array == NULL) {
        fprintf(stderr, "binary_insertion_create: unable to allocate memory for the ordered array");
        return NULL;
    }

    array->array = (void**)malloc(INITIAL_CAPACITY * sizeof(void*));
    if (array->array == NULL) {
      fprintf(stderr, "array_create: unable to allocate memory for the internal array");
      return NULL;
    }

    array->size = 0;   
    array->precedes = precedes;
    array->array_capacity = INITIAL_CAPACITY;
    return array;
}


Array *array_insert(Array *array, void *element) {
  if (array == NULL) {
    fprintf(stderr, "array_insert_element: array parameter cannot be NULL");
    return NULL;
  }
  if (element == NULL) {
    fprintf(stderr, "array_insert_element: element parameter cannot be NULL");
    return NULL;
  }
  
  if (array->size >= array->array_capacity) {
    array->array_capacity = 2 * array->array_capacity; 
    array->array = (void**)realloc(array->array, array->array_capacity * sizeof(void*));
    if (array->array == NULL) {
      fprintf(stderr,"array_insert: unable to reallocate memory to host the new element");
      return NULL;
    }
  }
  array->array[array->size] = element; //segfault
  array->size++;
  return array;
}

void ordered_array_free_memory(Array *array) {
  if (array == NULL) {
    fprintf(stderr, "array_free_memory: ordered_array parameter cannot be NULL");
    return;
  }
  free(array->array);
  free(array);
}
Array *bi_insertion_sort(Array *array){
  unsigned long i, index = 0;
  for(i = 1; i < array->size; i++){
    void *key = array->array[i];
    index = bi_get_index(array, 0, i - 1, key);  //get index of
    insert_element(array, key, index,i);
  }
  return array;
}

static unsigned long bi_get_index(Array *array, unsigned long a, unsigned long b, void *key){
  if(a == b){
    if(array->precedes(array->array[a], key) == 1){
      return a;
    }
    return b + 1;
  }
  else{
    unsigned long m = (b + a) / 2;
    if(array->precedes(array->array[m], key) == 1){
      return bi_get_index(array, a, m, key);
    }
    else if(array->precedes(array->array[m], key) == -1){
      return bi_get_index(array, m + 1, b, key);
    }
    else
      return m;
  }
}

static void insert_element(Array *array, void *element, unsigned long index, unsigned long b){
  for (unsigned long i = b; i > index; --i)
    array->array[i] = array->array[i-1];
  array->array[index] = element;
}