#include "sorting_lib.h"
#include <stdlib.h>
#include <time.h>
#include <stdio.h>


int choose_random_pivot = 0;

/*
APPUNTI:
Wquicksort e quicksort non devono restituire nulla, non serve neanche restituire NULL 
perchè non ci sarà mai errore (al massimo fallisce l'ordinamento).
_Array non deve esserci in sorting_lib (insieme a tutti i metodi che lo gestisono), andrà messo in un'altra libreria che gestisce solo quello
Siccome non abbiamo più _Array i metodi devono prendere più parametri per size, precedes ecc...
*/

/*METODI STATICI*/

static unsigned long bi_get_index(void *array, int (*precedes)(void*, void*), unsigned long a, unsigned long b, void *key);
static void insert_element(void *array, void *element, unsigned long index, unsigned long b);

static void *Wquicksort(void *array, long p, long r); /*Wrappedquicksort*/
static long partition(void *array, long p, long r);
static void *swap_val(void *array, long a, long b);
/*static int conta=0;*/

void *bi_insertion_sort(void *array, unsigned long size, int (*precedes)(void*, void*)){ 
  unsigned long i, index = 0;
  for(i = 1; i < size; i++){
    void *key = &array[i];
    index = bi_get_index(array, precedes, 0, i -1, key);
    insert_element(array, key, index,i);
  }
  return array;
}

static unsigned long bi_get_index(void *array, int (*precedes)(void*, void*), unsigned long a, unsigned long b, void *key){
  if(a == b){
    if(precedes(&array[a], key) == 1){
      return a;
    }
    return b + 1;
  }
  else{
    unsigned long m = (b + a) / 2;
    if(precedes(&array[m], key) == 1){
      return bi_get_index(array, precedes, a, m, key);
    }
    else if(precedes(&array[m], key) == -1){
      return bi_get_index(array, precedes, m + 1, b, key);
    }
    else
      return m;
  }
}

static void insert_element(void *array, void *element, unsigned long index, unsigned long b){
  for (unsigned long i = b; i > index; --i)
    &array[i] = &array[i-1]; 
    &array[index] = element; 
}

void *quicksort(void *array, int mode){
  if(mode == 0){
    choose_random_pivot = 0;
  }else if(mode == 1){
    choose_random_pivot = 1;
  }else{
    return NULL;
  }

  return Wquicksort(array, 0, (long) size-1);
}

static void *Wquicksort(void *array, long p, long r){
  long q;
  if(r - p >= 1){
    q = partition(array, p, r);
    //printf("PRIMA: p: %lu, q: %lu, r: %lu \n", p,q, r);
    if(q > p){/*q > 1*/
      array = Wquicksort(array, p, q - 1);
    }
    if(q < r){
      array = Wquicksort(array, q + 1, r);
    }
    //printf("DOPO: p: %lu, q: %lu, r: %lu \n", p, q, r);
  }
  return array;
}

static long partition(void *array, long p, long r){
  long i = p + 1, j = r;
  if(choose_random_pivot == 0){
    long rand_pivot;
    long int seed = time(NULL);
    srand((unsigned int) seed);
    /*if(p >= r){ r - p <= 1
      return j;
    }*/
    rand_pivot = (long)rand() % (r-p+1) + p;
    array = swap_val(array, rand_pivot, p); /*cambiato*/
  }

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

static void *swap_val(void *array,  long a,  long b){
  if(array == NULL)
    return NULL;
    
  void *temp=array->array[a];
  array->array[a] = array->array[b];
  array->array[b] = temp;
  return array;
}