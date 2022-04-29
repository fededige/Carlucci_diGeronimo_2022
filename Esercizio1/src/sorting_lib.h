typedef struct _Array Array; /*VEDIAMO CHE FARE*/


void *bi_insertion_sort(void *array, unsigned long size , int (*precedes)(void*, void*));
void *quicksort(void *array, int mode);
