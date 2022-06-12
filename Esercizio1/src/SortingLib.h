typedef struct _Array Array; 


void bi_insertion_sort(void ** array, unsigned long size , int (*precedes)(void*, void*));
void quicksort(void ** array, int mode, unsigned long size, int (*precedes)(void*, void*));
