#include "bi_insertion_sort.h"
#include <stdlib.h>
#include <stdio.h>

#define INITIAL_CAPACITY 2

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
  printf("%ld", array->size);
  array->array[array->size] = element;
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