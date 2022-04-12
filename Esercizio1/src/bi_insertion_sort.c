#include "bi_insertion_sort.h"
#include <stdlib.h>
#include <stdio.h>

struct _Array {
  void **array;
  unsigned long size;
  int (*precedes)(void*, void*);
};



Array *binary_insertion_create(int *(precedes) (void *a, void *b)){
    if (precedes == NULL) {
        fprintf(stderr, "ordered_array_create: precedes parameter cannot be NULL");
        return NULL;
    }
    Array *array = (Array*)malloc(sizeof(Array));

    if (array == NULL) {
        fprintf(stderr, "ordered_array_create: unable to allocate memory for the ordered array");
        return NULL;
    }

    array->size = 0;
    array->precedes = precedes;
    return array;
}


