#include "bi_insertion_sort.h"
#include <stdlib.h>
#include <time.h>
#include <stdio.h>

#define INITIAL_CAPACITY 2

/*METODI STATICI*/

static unsigned long bi_get_index(Array *array, unsigned long a, unsigned long b, void *key);
static void insert_element(Array *array, void *element, unsigned long index, unsigned long b);

static Array *Wrand_quicksort(Array *array, long p, long r);
static long rand_partition(Array *array, long p, long r);
static Array *swap_val(Array *array, long a, long b);
/*static int conta=0;*/
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
        fprintf(stderr, "binary_insertion_create: unable to allocate memory for the array");
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

int array_is_empty(Array *array) {
  if (array == NULL) {
    return 1;
  }
  return array->size == 0;
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

void array_free_memory(Array *array) {
  if (array == NULL) {
    fprintf(stderr, "array_free_memory: array parameter cannot be NULL");
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

Array *rand_quicksort(Array *array){
  return Wrand_quicksort(array, 0, (long) array->size-1);
}

static Array *Wrand_quicksort(Array *array, long p, long r){
  long q;
  if(r > 0){
    q = rand_partition(array, p, r);
    if(q > 1){
      array = Wrand_quicksort(array, p, q - 1);
    }
    if(q < r){
      array = Wrand_quicksort(array, q + 1, r);
    }
  }
  return array;
}

/*static Array *Wrand_quicksort(Array *array, long p, long r){
  long q;
  if(p < r){
    q = rand_partition(array, p, r);
    array = Wrand_quicksort(array, p, q - 1);
    array = Wrand_quicksort(array, q + 1, r);
  }
  return array;
}*/

static long rand_partition(Array *array, long p, long r){
  long rand_pos, i = p + 1, j = r;
  long int seed = time(NULL);
  srand((unsigned int) seed);
  if(p >= r){
    return j;
  }
  rand_pos = (long)rand() % (r-p+1) + p;
  /*printf("RANDZ: %li \n",rand_pos);*/
  array = swap_val(array, rand_pos, p); /*cambiato*/
  
  while(i <= j){
    if(array->precedes(array->array[i], array->array[p]) == -1 || array->precedes(array->array[i], array->array[p]) == 0){//A[i] <= A[p] /*cambiato*/
      i++;
    }
    else{
      if(array->precedes(array->array[j], array->array[p]) == 1){//A[j] > A[p] /*cambiato*/
        j--;         
      }
      else{
        array = swap_val(array, i, j); /* verificare j o j - 1 cambiato*/
        i++;
        j--;
      }
    }                 
  }
  array = swap_val(array, p, j); /*cambiato*/
  return j;
}

/*static long rand_partition(Array *array, long p, long r){
  long rand_pos, i = 0, j;
  long int seed = time(NULL);
  srand((unsigned int) seed);
  rand_pos = (long)rand()% (r-p+1) + p;
  printf("RANDZ: %li \n",rand_pos);
  //x = array->array[rand_pos];
  array = swap_val(array, rand_pos, r);
  
  if(array == NULL){
    fprintf(stderr, "errore in swap");
  }
  
  i = p - 1;
  for(j = p; j < r - 1; j++){
    if(array->precedes(array->array[j], array->array[r]) == -1 || array->precedes(array->array[j], array->array[r]) == 0){ //A[j] <= x //array->array[r]
      printf("entro qwer\n");
      i = i + 1;
      array = swap_val(array, i, j);
      if(array == NULL){
        fprintf(stderr, "errore in swap");
      }
    }
    array = swap_val(array, i + 1, r);
    if(array == NULL){
      fprintf(stderr, "errore in swap");
    }
  }
  return i + 1;
}*/

static Array *swap_val(Array *array,  long a,  long b){
  if(array == NULL)
    return NULL;
    
  void *temp=array->array[a];
  array->array[a] = array->array[b];
  array->array[b] = temp;
  return array;
}