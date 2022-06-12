#include "GenericDynamicArray_lib.h"
#include <stdlib.h>
#include <stdio.h>

//ritorna l'elemento in posizione i dell'array
void *array_get(Array *array, unsigned long i) {
    if(array == NULL){
        fprintf(stderr, "array_get: array parameter cannot be NULL");
        return NULL;
    }
    if(i >= array->size){
        fprintf(stderr, "array_get: Index %lu is out of the array bounds", i);
        return NULL;
    }
    return array->array[i];
}
// ritorna la size dell'array 
unsigned long array_size(Array *array) {
  if(array == NULL){
    return 0;
  }
  return array->size;
}
//creazione di un array vuoto
Array *array_create(int (*precedes) (void *a, void *b)){
    if(precedes == NULL){
        fprintf(stderr, "array_create: precedes parameter cannot be NULL");
        return NULL;
    }

    Array *array = (Array*)malloc(sizeof(Array));

    if(array == NULL){
        fprintf(stderr, "array_create: unable to allocate memory for the array");
        return NULL;
    }

    array->array = (void**)malloc(INITIAL_CAPACITY * sizeof(void*));
    if(array->array == NULL){
        fprintf(stderr, "array_create: unable to allocate memory for the internal array");
        return NULL;
    }

    array->size = 0;   
    array->precedes = precedes;
    array->array_capacity = INITIAL_CAPACITY;
    return array;
}
//controlla se l'array è vuoto
int array_is_empty(Array *array) {
    if(array == NULL){
        return 1;
    }
    return array->size == 0;
}
//inserimento di un nuovo elemento
Array *array_insert(Array *array, void *element) {
    if(array == NULL){
        fprintf(stderr, "array_insert_element: array parameter cannot be NULL");
        return NULL;
    }
    if(element == NULL){
        fprintf(stderr, "array_insert_element: element parameter cannot be NULL");
        return NULL;
    }
    
    if(array->size >= array->array_capacity){
        array->array_capacity = 2 * array->array_capacity; //raddoppia la capacità dell'array
        array->array = (void**)realloc(array->array, array->array_capacity * sizeof(void*));
        if (array->array == NULL) {
            fprintf(stderr,"array_insert: unable to reallocate memory to host the new element");
            return NULL;
        }
    }
    array->array[array->size] = element;
    array->size++;
    return array;
}
//deallocazione dell'array
void array_free_memory(Array *array) {
    if(array == NULL){
        fprintf(stderr, "array_free_memory: array parameter cannot be NULL");
        return;
    }
    free(array->array);
    free(array);
}